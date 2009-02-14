import de.randi2test.helpers.InitializationHelper as init
import de.randi2test.helpers.RandomizationHelper
import de.randi2.randomization.*
import de.randi2.model.randomization.*


class Helper {
    static createTrialWithArms(armSizes, map = [:]) {
        def conf = init.createBlockRandomConf(map)
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

scenario "4 random allocations", {
    given "a trial with two equally sized trial arms", {
        res = Helper.createTrialWithArms([40,40], [
                minimum: 2, maximum: 2
            ])
        trial = res.first
        arms = res.last
    }
    when "4 subjects are allocated", {
        4.times {
            trial.randomize init.createTrialSubject()
        }
    }
    then "the first arm should have 2 subjects ", {
        arms[0].subjects.size().shouldBe 2
    }
    and "the second arm should have 2 subjects", {
        arms[1].subjects.size().shouldBe 2
    }
}

scenario "400 random allocations", {
    given "a trial with two equally sized trial arms (block size * 2)", {
        res = Helper.createTrialWithArms([200,200], [
                minimum: 2, maximum: 2
            ])
        trial = res.first
        arms = res.last
    }
    when "4 subjects are allocated", {
        400.times {
            trial.randomize init.createTrialSubject()
        }
    }
    then "the first arm should have 200 subjects ", {
        arms[0].subjects.size().shouldBe 200
    }
    and "the second arm should have 200 subjects", {
        arms[1].subjects.size().shouldBe 200
    }
}

scenario "400 random allocations", {
    given "a trial with two equally sized trial arms", {
        res = Helper.createTrialWithArms([200,200], [
                minimum: 1, maximum: 2
            ])
        trial = res.first
        arms = res.last
    }
    when "4 subjects are allocated", {
        400.times {
            trial.randomize init.createTrialSubject()
        }
    }
    then "the first arm should have 198 to 201 subjects ", {
        arms[0].subjects.size().shouldBeLessThan 202
        arms[0].subjects.size().shouldBeGreaterThan 197
    }
    and "the second arm should have 198 to 201 subjects", {
        arms[1].subjects.size().shouldBeLessThan 202
        arms[1].subjects.size().shouldBeGreaterThan 197
    }
}

scenario "400 random allocations", {
    given "a trial with two equally sized trial arms", {
        res = Helper.createTrialWithArms([20,20], [
                minimum: 15, maximum: 20,
                type: BlockRandomizationConfig.TYPE.ABSOLUTE
            ])
        trial = res.first
        arms = res.last
    }
    when "4 subjects are allocated", {
        20.times {
            trial.randomize init.createTrialSubject()
        }
    }
    then "the first arm should have 7 to 13 subjects ", {
        arms[0].subjects.size().shouldBeLessThan 14
        arms[0].subjects.size().shouldBeGreaterThan 6
    }
    and "the second arm should have 7 to 13 subjects", {
        arms[1].subjects.size().shouldBeLessThan 14
        arms[1].subjects.size().shouldBeGreaterThan 6
    }
}