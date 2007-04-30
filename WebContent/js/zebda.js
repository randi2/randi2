/*

Zebda javascript library, version 0.3.1
 http://labs.cavorite.com/zebda
Copyright (C) 2006 Juan Manuel Caicedo

Contributors:
 - Chris Wilson http://www.christopherwilson.net

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA


See http://www.gnu.org/copyleft/lesser.html for more information

*/
 
var Zebda = {
  Version: '0.3.1'
};

 

/*--------------------------------------------------------------------------*/
/*
Zebda javascript library
http://labs.cavorite.com/zebda
Copyright (C) 2006 Juan Manuel Caicedo

See zebda.js for full licence

*/

// Common functions

/**
 * Simple JSON parser
 */
if (!window.JSON){
    JSON = {
        parse: function(str){
            return eval('(' + str + ')');
        }
    };
}

/**
 * Removes trailing withspace
 */
String.prototype.trim = function(){
    return this.replace(/ /g, '');
};


/**
 * Javascript sets
 * 
 * Example:
 * var typeInSet = nodeType in set(2, 3, 4, 7, 8);
 *
 * http://laurens.vd.oever.nl/weblog/items2005/setsinjavascript/
 */
function set (){
    var result = {};
    for (var i = 0; i < arguments.length; i++){
        result[arguments[i]] = true;
    }
    return result;
}


/** 
 * Finds the position of a node
 *
 */
Element.getSiblingPosition = function(element){
    var pos = 0;
    while (element.previousSibling) {
        if (element.previousSibling.nodeType != 3){
            pos++;
        }
        element = element.previousSibling;
    }
    return pos;
};


/**
 * 
 *
 */
Element.getParentNodeByName = function(element, tagName){
    var par = element;
    while(par.tagName.toLowerCase() != tagName && par.parentNode) {
        par = par.parentNode;
    }
    return par;
};

/**
 *
 * @see    http://www.litotes.demon.co.uk/example_scripts/fastTableSort_src.html
 */
Element.getInnerText = function(element){
    if (!element || (typeof element).search(/string|undefined/) > -1)
        return element;

    if (element.innerText)
        return element.innerText;

    var rtn = '';
    var nodes = element.childNodes;
    var len = nodes.length;
    for (var i = 0; i < len; i++) {
        switch (nodes[i].nodeType) {
            case 1: //ELEMENT_NODE
                rtn += Element.getInnerText(nodes[i]);
                break;
            case 3: //TEXT_NODE
                rtn += nodes[i].nodeValue;
                break;
        }
    }
    return rtn;
};


/**
 * Sets the value of a form field
 * 
 */
Form.Element.setValue = function(element, value){
    element = $(element);
    els = ((element.tagName)) && [element] || element;

    $A(els).each(function(element){
        var elmType = element.tagName.toLowerCase();
        switch(elmType){
            case 'select':
                if (typeof(value) != 'object')
                    value = [value];

                $A(element.options).each(function(opt){
                    opt.selected = value.include(opt.value);
                });
                break;
            case 'input':
                if (element.type in set('radio', 'checkbox')){
                    element.checked = (element.value == value);
                }else{
                    element.value = value;
                }
        }
    });
}
/*--------------------------------------------------------------------------*/
/*
Zebda javascript library
http://labs.cavorite.com/zebda
Copyright (C) 2006 Juan Manuel Caicedo

See zebda.js for full licence

*/

var FormValidator = {

    /*
        Namespace prefix
    */
    NSprefix: 'z',

    defaultOptions : {
        inline: false,
        inlineFilters: false
    },

    /**
     * Load the defined condition and filters
     * Find all the forms with a validation rule and modify theirs onsubmit function
     */
    init: function(){
        this._definedConditions = [];
        for (name in FormValidator.Conditions){
            this._definedConditions.push(name);
        }

        this._definedFilters = [];
        for (name in FormValidator.Filters){
            this._definedFilters.push(name);
        }

        var forms = $A(document.forms);

        //Modify onsubmit function for forms with defined rules
        forms.each(function(f){
            f.onsubmit = function(){
                try {
                    FormValidator.initForm(f);
                    f.beforeValidate();
                    if (FormValidator.validate(f)){
                        return f.afterValidate();
                    }else{
                        return false;
                    }
                } catch (e){
                    return false;
                }
            };

            var disp = f.getAttribute('z:display');
            f._displayFunction = (FormValidator.Display[disp] || FormValidator.Display.alert);
        });
    },

    /**
     * Configures a form
     *
     */
    initForm: function(frm){

        //Validation options
        frm._options = {};
        for (opc in FormValidator.defaultOptions){
            frm._options[opc] = FormValidator.defaultOptions[opc];
        }
        opts = eval('(' + frm.getAttribute('z:options') + ')') || {};
        for (opc in opts){
            frm._options[opc] = opts[opc];
        }

        //Before and after validate functions
        if (!frm.beforeValidate){
            func = frm.getAttribute('z:beforeValidate') || 'return true';
            frm.beforeValidate = new Function(func);
        }

        if (!frm.afterValidate){
            func = frm.getAttribute('z:afterValidate') || 'return true';
            frm.afterValidate = new Function(func);
        }

        rtn = false;
        //Configure elements
        Form.getElements(frm).each(function(elm){
            FormValidator.Element.init(elm);
            if (!rtn && FormValidator.Element.hasRules(elm)){
                rtn = true;
            }
        });
        return rtn;
    },

    /**
     * Validates a form
     *
     */
    validate: function(form){
        var errs = Form.getElements(form).map(FormValidator.Element.validate).flatten();
        if (errs.length > 0){
            form._displayFunction(errs);
            return false;
        }
        return true;
    },

    /**
     * Functions for display errors
     *
     */
    Display: {

        /*
         * Show inline errors.
         * Based on examples by Cameron Adams:
         * http://www.themaninblue.com/writing/perspective/2005/10/05/
         */
        inline: function(errs){
            $A(document.getElementsByClassName('_zebda_message')).each(function (elm){
                elm.parentNode.removeChild(elm);
            });
            errs.map(function(e){
                var t = document.createElement('span');
                t.className = '_zebda_message correctionText warning';
                t.appendChild(document.createTextNode(e.message));
                e.element.parentNode.insertBefore(t, e.element.nextSibling);
            });
        },

        /**
         * Show an alert with all errors
         */
        alert: function(errs){
            alert(errs.pluck('message').join('\n'));
        }
    },

    /**
     * Conditions for the rules
     */
    Conditions: {
        required: function(value){
            return String(value).length > 0;
        },

        length: function(value){
            var rtn = true;
            value = (value || '');
            if (this.options.max)
                rtn = (value.length <= this.options.max);

            if (this.options.min)
                rtn = rtn && (value.length >= this.options.min);

            return rtn;
        },

        numeric: function(value){
            var rtn, val;

            rtn = true;
            val = (this.options.isFloat) ? parseFloat(value) : parseInt(value);

            if (isNaN(val))
                return false;

            if (!(this.options.maxValue === undefined))
                rtn = rtn && (this.options.maxValue > val);

            if (!(this.options.minValue === undefined))
                rtn = rtn && (val > this.options.minValue);

            return rtn;
        },

        /**
         *
         */
        regexp: function(value){
            var reg = (this.options.constructor == RegExp) ? this.options : this.options.exp;
            return reg.test(value);
        },

        /**
         * Email regular expression
         * bilou mcgyver
         * http://www.regexlib.com/REDetails.aspx?regexp_id=333
         */
        email: function(value){
            var expMail = /^[\w](([_\.\-\+]?[\w]+)*)@([\w]+)(([\.-]?[\w]+)*)\.([A-Za-z]{2,})$/;
            return expMail.test(value);
        },

        /**
         * Compare current value with other element
         * 
         */
        compare: function(value){
            return false;
        },

        /**
         * Apply the rule only when a condition is satisfied
         * TODO
         */
        conditional: function(value){
            return false;
        }
    },

    /**
     * Filter for text inputs
     *
     */
    Filters: {
        trim: function(value){
            return FormValidator.Filters.trimleft(FormValidator.Filters.trimright(value));
        },

        trimleft: function(value){
            return new String(value).replace(/^\s+/, '');
        },

        trimright: function(value){
            return new String(value).replace(/\s+$/, '');
        },

        /**
         * Replaces the double spaces for single space
         *
         */
        singlespace: function(value){
            return new String(value).replace(/(\s{2,})/g,' ');
        },

        lowercase: function(value){
            return new String(value).toLowerCase();
        },

        uppercase: function(value){
            return new String(value).toUpperCase();
        },

        numbers: function(value){
            return new String(value).replace(/([^0-9])/g, '');
        }
    }
 }

var Rule = Class.create();
Rule.prototype = {
    initialize: function(element, ruleName, options, message){
        this.condition = Prototype.K;
        this.message = (message || '');

        options = options || {};
        this.options = (typeof(options) == 'string') && eval('(' + options + ')') || options;
        this.element = element;

        if (typeof(ruleName) == 'string'){
            this.condition = FormValidator.Conditions[ruleName]; //no bind needed
        } else {
            this.condition = ruleName;
        }
    },

    evaluate: function(){
        return this['condition'].call(this, Form.Element.getValue(this.element));
    }
};

Rule.Conditions = {

    /**
     * The value is required
     */
    required: function(value){
        return value;
    },
    length: function(value){
        var rtn = true;
        value = (value || '');
        if (this.options.max)
            rtn = (value.length <= this.options.max);

        if (this.options.min)
            rtn = rtn && (value.length >= this.options.min);

        return rtn;
    }
}

var Filter = Class.create()
Filter.prototype = {
    initialize: function(element, filter, options){
        this.element = element;
        this.options = eval('(' + options + ')');
        this.evaluate = FormValidator.Filters[filter];
    }
}

FormValidator.Error = Class.create();
FormValidator.Error.prototype = {
    initialize: function(element, message){
        this.element = element;
        this.message = message;
    },

    inspect: function() {
        return '#<FormValidator.Error:element=' + this.element + ',message=' + this.message + '>';
    }
}

Object.extend(FormValidator,{Element: {
    /**
     *
     */
    init: function(element){
        element._rules = _definedConditions.map(function(cond){
            if (condOptions = element.getAttribute(FormValidator.NSprefix + ':' + cond)){
                var msg = (element.getAttribute(FormValidator.NSprefix + ':' + cond + '_message')
                     || element.getAttribute(FormValidator.NSprefix + ':' + 'message'));
                return new Rule(element, cond, condOptions, msg);
            }
        }).compact()

        element._filters = _definedFilters.map(function(filter){
            if (filterValue = element.getAttribute(FormValidator.NSprefix + ':filter_' + filter)){
                return new Filter(element, filter, filterValue);
            }
        }).compact()

        //Inline validation
        if (element.form._options.inline){
            Event.observe(element, 'change', function(){
                element.form['_displayFunction'](FormValidator.Element.validate(this));
                element.focus();
            })
        }

        //Inline filters
        if (element.form._options.inlineFilters){
            Event.observe(element, 'change', function(){
                FormValidator.Element.applyFilters(element);
            })
        }
    },

    addRule: function(element, rule, inline){
        element._rules.push(rule);
    },

    addFilter: function(element, filter, inline){
        element._filters.push(filter);
    },

    /**
     * Returns true if the element has any attribute with the namespace
     * prefix
     */
    hasRules: function(element){
        return (element._rules || false);
    },

    /**
     *
     *
     */
    getRules: function(element){
        return (element._rules || []);
    },

    getFilters: function(element){
        return (element._filters || []);
    },

    isText: function(element){
        return ((element.tagName.toLowerCase() == 'input' &&
                    (element.type.toLowerCase() in set('text','hidden','password'))) ||
                    (element.tagName.toLowerCase() == 'textarea'));
    },

    /**
     * Filter can only be applied on textboxes, passwords and textarea
     */
    applyFilters: function(element){
        if (FormValidator.Element.isText(element)){
            FormValidator.Element.getFilters(element).each(function(filter){
                element.value = filter.evaluate(element.value)
            })
        }
    },

    /**
     * Returns the error objects produced during the validation
     *
     */
    validate: function(element){
        FormValidator.Element.applyFilters(element);
        return FormValidator.Element.getRules(element).map(function(rule){
            if (!rule.evaluate())
                return new FormValidator.Error(element, rule.message);
        }).compact()
    }
}})

/*
Initialize FormValidator after the page has loaded
*/
Event.observe(window,'load',FormValidator.init);
/*--------------------------------------------------------------------------*/