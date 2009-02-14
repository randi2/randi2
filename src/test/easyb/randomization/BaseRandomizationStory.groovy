import de.randi2.model.*
import de.randi2.model.randomization.*
import de.randi2.randomization.*

import de.randi2test.helpers.RandomizationHelper
import de.randi2test.helpers.InitializationHelper as init

scenario "complete randomization initialization", {
    given "a trial", {
        trial = init.createTrial()
    }
    and "a base randomization configuration for complete rand.", {
        conf = init.createBaseRandomConf(algorithmClass: CompleteRandomization)
    }
    when "the conf is set to the trial", {
        trial.randomizationConfiguration = conf
    }
    then "the trials random algorithm should be a complete rand.", {
        conf.getAlgorithm(trial).shouldBeA CompleteRandomization
    }
}


scenario "unallocated subject", {
    given "a trial with two equally sized trial arms", {
        subject = init.createTrialSubject()
    }
    then "the subjects arm propertiy should be null", {
        subject.arm.shouldBe null
    }
}