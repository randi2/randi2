/**
 * @class Ext.form.MiscField
 * @extends Ext.Component
 * Base class to easily display simple text in the form layout.
 * @constructor
 * Creates a new MiscField Field 
 * @param {Object} config Configuration options
 */
Ext.form.MiscField = function(config){
    Ext.form.MiscField.superclass.constructor.call(this, config);
};

Ext.extend(Ext.form.MiscField, Ext.Component,  {
    /**
     * @cfg {String/Object} autoCreate A DomHelper element spec, or true for a default element spec (defaults to
     * {tag: "div"})
     */
    defaultAutoCreate : {tag: "div"},
    /**
     * @cfg {String} fieldClass The default CSS class for the field (defaults to "x-form-field")
     */
    fieldClass : "x-form-field",

    // private
    isFormField : true,

    /**
     * Returns the name attribute of the field if available
     * @return {String} name The field name
     */
    getName : function(){
        return this.rendered && this.el.dom.name ? this.el.dom.name : (this.hiddenName || '');
    },

    /**
     * Apply the behaviors of this component to an existing element. <b>This is used instead of render().</b>
     * @param {String/HTMLElement/Element} el The id of the node, a DOM Node or an existing Element
     * @return {Ext.form.MiscField} this
     */
    applyTo : function(target){
        this.target = target;
        this.el = Ext.get(target);
        this.render(this.el.dom.parentNode);
        return this;
    },

    // private
    onRender : function(ct){
        if(this.el){
            this.el = Ext.get(this.el);
            if(!this.target){
                ct.dom.appendChild(this.el.dom);
            }
        }else {
            var cfg = this.getAutoCreate();
            if(!cfg.name){
                cfg.name = this.name || this.id;
            }
            this.el = ct.createChild(cfg);
        }

	this.el.addClass('x-form-miscfield');

        if(!this.customSize && (this.width || this.height)){
            this.setSize(this.width || "", this.height || "");
        }
        if(this.style){
            this.el.applyStyles(this.style);
            delete this.style;
        }

        this.el.addClass([this.fieldClass, this.cls]);
        this.initValue();
    },

    // private
    initValue : function(){
        if(this.value !== undefined){
            this.setRawValue(this.value);
        }else if(this.el.dom.innerHTML.length > 0){
            this.setRawValue(this.el.dom.innerHTML);
        }
    },

    // private
    afterRender : function(){
        this.initEvents();
    },

    /**
     * Resets the current field value to the originally-loaded value
     */
    reset : function(){
        this.setRawValue(this.originalValue);
    },

    // private
    initEvents : function(){
        // reference to original value for reset
        this.originalValue = this.getRawValue();
    },

    /**
     * Sets the height and width of the field
     * @param {Number} width The new field width in pixels
     * @param {Number} height The new field height in pixels
     */
    setSize : function(w, h){
        if(!this.rendered || !this.el){
            this.width = w;
            this.height = h;
            return;
        }
        if(w){
            this.el.setWidth(w);
        }
        if(h){
            this.el.setHeight(h);
        }
        var h = this.el.dom.offsetHeight; // force browser recalc
    },

    /**
     * Validates the field value
     * Always returns true, MiscField cannot be marked invalid
     * Required for successful form submission
     * @return True
     */
    validate : function(){
        return true;
    },

    /**
     * Clear any invalid styles/messages for this field
     * Empty function, MiscField cannot be marked invalid
     * Required for certain form actions
     */
    clearInvalid : function(){
        return;
    },

    /**
     * Returns the raw field value.
     * @return {Mixed} value The field value
     */
    getRawValue : function(){
        return this.el.dom.innerHTML;
    },

    /**
     * Returns the clean field value - plain text only, strips out HTML tags.
     * @return {Mixed} value The field value
     */
    getValue : function(){
        var f = Ext.util.Format;
        var v = f.trim(f.stripTags(this.getRawValue()));
        return v;
    },

    /**
     * Sets the raw field value.
     * @param {Mixed} value The value to set
     */
    setRawValue : function(v){
        this.value = v;
        if(this.rendered){
            this.el.dom.innerHTML = v;
        }
    },

    /**
     * Sets the clean field value - plain text only, strips out HTML tags.
     * @param {Mixed} value The value to set
     */
    setValue : function(v){
        var f = Ext.util.Format;
	this.setRawValue(f.trim(f.stripTags(v)));
    }
});