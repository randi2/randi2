package de.randi2test.randomization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.randi2test.helpers.*

import de.randi2.model.*
import de.randi2.model.randomization.*
import de.randi2.randomization.*


scenario "two equally sized arms", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with equally sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [10,10])
        trial = res.first
        arms = res.last
    }
    then "the block should contain both arms once", {
        conf.algorithm.generateRawBlock().shouldBe arms
    }
}

scenario "tree equally sized arms", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with tree equally sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [100,100, 100])
        trial = res.first
        arms = res.last
    }
    then "the block should contain all arms once", {
        conf.algorithm.generateRawBlock().shouldBe arms
    }
}

scenario "four equally sized arms", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with four equally sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [20, 20,20,20])
        trial = res.first
        arms = res.last
    }
    then "the block should contain all arms once", {
        conf.algorithm.generateRawBlock().shouldBe arms
    }
}

scenario "two diffent sized arms", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with 66/33 sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [66,33])
        trial = res.first
        arms = res.last
    }
    then "the block should contain the first one twice, the second one once", {
        conf.algorithm.generateRawBlock().shouldBe([arms[0], arms[0], arms[1]])
    }
}

scenario "tree diffent sized arms", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with 50/25/25 sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [50, 25, 25])
        trial = res.first
        arms = res.last
    }
    then "the block should containt the first one twice, the other ones once", {
        conf.algorithm.generateRawBlock().shouldBe([arms[0], arms[0], arms[1], arms[2]])
    }
}
scenario "totally different sized", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with 43/197 sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [43,197])
        trial = res.first
        arms = res.last
    }
    then "the block should contain the first on 43 times, the second one 197 time", {
        conf.algorithm.generateRawBlock().shouldBe([arms[0]]*43 + [arms[1]]*197)
    }
}



