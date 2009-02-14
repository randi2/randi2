package de.randi2test.randomization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.randi2test.helpers.*

import de.randi2.model.*
import de.randi2.model.randomization.*
import de.randi2.randomization.*


scenario "", {
    given "any algorithm", {
        conf = new DummyRandomizationConfig()
    }
    and "a trial with equally sized arms", {
        res = RandomizationHelper.createTrialWithArms(conf, [10,10])
        trial = res.first
        arms = res.last
    }
    then "", {
        arms.shouldBe conf.algorithm.generateRawBlock()
    }
}