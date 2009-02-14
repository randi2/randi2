import de.randi2.model.*
import de.randi2.model.randomization.*
import de.randi2.randomization.*
import de.randi2.utility.*

class Helper {
    static create_trial_with_arms(arm_sizes) {
        def trial = new Trial()
        def conf = new BaseRandomizationConfig()
        conf.algorithmClass = CompleteRandomization
        trial.randomizationConfiguration = conf
        def treatmentArms = []
        arm_sizes.each { size ->
            def arm = new TreatmentArm()
            arm.setPlannedSubjects size
            treatmentArms << arm
        }
        trial.treatmentArms = treatmentArms
        return new Pair(trial, treatmentArms)
    }
}


scenario "complete randomization initialization", {
    given "a trial", {
        trial = new Trial()
    }
    and "a base randomization configuration for complete rand.", {
        conf = new BaseRandomizationConfig()
        conf.algorithmClass = CompleteRandomization
    }
    when "the conf is set to the trial", {
        trial.randomizationConfiguration = conf
    }
    then "the trials random algorithm should be a complete rand.", {
        conf.getAlgorithm(trial).shouldBeA CompleteRandomization
    }
}




scenario "one subject allocation", {
    given "a trial with two equally sized trial arms", {
        res = Helper.create_trial_with_arms([10,10])
        trial = res.first
        arms = res.last
    }
    when "a subject is randomized", {
        subject = new TrialSubject()
        trial.randomize subject
    }
    then "the subjects arm should be one of the arms of the trials", {
        ensure(arms) { contains(subject.arm)}
    }
}