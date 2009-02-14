/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2test.randomization
import de.randi2.randomization.*
import de.randi2.model.*
import de.randi2.model.randomization.*

/**
 *
 * @author jthoenes
 */
class DummyRandomization extends RandomizationAlgorithm {
	protected DummyRandomization(Trial _trial) {
        super(_trial);
    }

    @Override
    protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * Breaking security for testing purpose
     */
    @Override
    public List<TreatmentArm> generateRawBlock() {
        return super.generateRawBlock();
    }
}

class DummyRandomizationConfig extends AbstractRandomizationConfig {
    public DummyRandomization createAlgorithm() {
        new DummyRandomization(super.getTrial())
    }
}

