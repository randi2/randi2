import de.randi2.model.*
import de.randi2.model.randomization.*
import de.randi2.randomization.*

import de.randi2test.helpers.RandomizationHelper
import de.randi2test.helpers.InitializationHelper as init

class Helper {
    static createTrialWithArms(armSizes) {
        def conf = init.createBiasedCoinRandomConf()
        return RandomizationHelper.createTrialWithArms(conf, armSizes)
    }
}

scenario "one subject allocation", {
    given "a trial with two equally sized trial arms", {
        res = Helper.createTrialWithArms([10,10])
        trial = res.first
        arms = res.last
    }
    when "a subject is randomized", {
        subject = init.createTrialSubject()
        trial.randomize subject
    }
    then "the subjects arm should not be null", {
        subject.arm.shouldNotBe null
    }
    and "the subjects arm should be one of the arms of the trials", {
        ensure(arms) { contains(subject.arm)}
    }
}

scenario "one subject allocation", {
    given "a trial with two equally sized trial arms", {
        res = Helper.createTrialWithArms([20,20])
        trial = res.first
        arms = res.last
    }
    when "40 subjects are randomized", {
        40.times {
            trial.randomize init.createTrialSubject()
        }
    }
    then "the treatment arms should have 40 subjects", {
        arms*.subjects*.size().sum().shouldBe 40
    }
    and "the trial should have 40 subjects", {
        trial.subjects.size().shouldBe 40
    }
}