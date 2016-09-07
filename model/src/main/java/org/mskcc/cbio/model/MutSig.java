/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mskcc.cbio.model;

import org.mskcc.cbio.model.summary.MutSigSummary;

/**
 *
 * @author ochoaa
 */
public class MutSig extends MutSigSummary {
    
    private CancerStudy cancerStudy;
    private Gene gene;

    /**
     * @return the cancerStudy
     */
    public CancerStudy getCancerStudy() {
        return cancerStudy;
    }

    /**
     * @param cancerStudy the cancerStudy to set
     */
    public void setCancerStudy(CancerStudy cancerStudy) {
        this.cancerStudy = cancerStudy;
    }

    /**
     * @return the gene
     */
    public Gene getGene() {
        return gene;
    }

    /**
     * @param gene the gene to set
     */
    public void setGene(Gene gene) {
        this.gene = gene;
    }
    
}
