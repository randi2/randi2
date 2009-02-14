package de.randi2test.helpers
import de.randi2.model.*
import de.randi2.model.randomization.*
import de.randi2.model.enumerations.*

/**
 *
 * @author jthoenes
 */
class InitializationHelper {

    private InitializationHelper(){throw new RuntimeException()}

    static int counter = 0;

    private static next(){
        counter++;
    }

    private static notNull(givenValue, defaultValue){
        (givenValue != null) ? givenValue : defaultValue
    }
    
    static createPerson(map = [:]){
        def p = new Person()
        p.surname = notNull(map['surname'], "Doe${next()}")
        p.firstname = notNull(map['firstname'], "John${next()}")
        p.eMail = notNull(map['eMail'], "abc${next()}@def.xy")
        p.gender = notNull(map['gender'], Gender.MALE)
        p.phone = notNull(map['phone'], "01234/6789${next()}")

        p
    }

    static createTrialSite(map = [:]){
        def c = new TrialSite()
        c.name = notNull(map['name'], "MyTrialSite${next()}")
        c.password = notNull(map['password'], "19oNAI#1NXOjU${next()}")
        c.contactPerson = notNull(map['contactPerson'], createPerson())
        
        c
    }

    static createTrial(map = [:]) {
        def t = new Trial()
        t.name = notNull(map['name'], "MyTrial${next()}")
        t.sponsorInvestigator = notNull(map['sponsorInvestigator'], createPerson())
        t.startDate = notNull(map['startDate'], new GregorianCalendar(2010, Calendar.JANUARY, 1))
        t.endDate = notNull(map['endDate'], new GregorianCalendar(2013, Calendar.DECEMBER, 31))
        t.leadingSite = notNull(map['leadingSite'], createTrialSite())

        t
    }

    static createLogin(map = [:]){
        def l = new Login()
        l.username = notNull(map['username'], "jdoe${next()}")
        l.password = notNull(map['password'], "19oNAI#1NXOjU${next()}")
        l.person = notNull(map['person'], createPerson())
        l.registrationDate = notNull(map['registrationDate'], new GregorianCalendar())
        l.lastLoggedIn = notNull(map['lastLoggedIn'], new GregorianCalendar())

        l
    }

    static createBaseRandomConf(map = [:]){
        def c = new BaseRandomizationConfig()
        c.algorithmClass = notNull(map['algorithmClass'], de.randi2.randomization.CompleteRandomization)
        c
    }

    static createTrialSubject(map = [:]){
        def ts = new TrialSubject()

        ts
    }

    static createTreatmentArm(map = [:]){
        def ta = new TreatmentArm()
        ta.name = notNull(map['name'], "MyTrialArm${next()}")
        ta.plannedSubjects = notNull(map['plannedSubjects'], 20)

        ta
    }

}

