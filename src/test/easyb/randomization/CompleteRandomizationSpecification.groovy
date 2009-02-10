import de.randi2.model.TreatmentArm
import de.randi2.model.Trial
import de.randi2.model.TrialSubject
import de.randi2.model.randomization.BaseRandomizationConfig
import de.randi2.randomization.CompleteRandomization


def trial

def getTA(size) {
    def ta = new TreatmentArm()
    ta.setPlannedSubjects size

    ta
}

before "", {
    trial = new Trial()
    def conf = new BaseRandomizationConfig()
    conf.algorithmClass = CompleteRandomization.class
    trial.randomizationConfiguration = conf
}

it "Initialization", {
    def aTrial = new Trial()
    def conf = new BaseRandomizationConfig()
    conf.setAlgorithmClass CompleteRandomization.class
    
    conf.getAlgorithm(aTrial).shouldBeA CompleteRandomization
}

it "should", {
    def ta1 = getTA(10)
    def ta2 = getTA(10)
    trial.treatmentArms = [ta1, ta2]
    def subject = new TrialSubject()

    subject.arm.shouldBe null

    trial.randomize(subject)
    ensure([ta1,ta2]){
        contains(subject.arm)
    }
}