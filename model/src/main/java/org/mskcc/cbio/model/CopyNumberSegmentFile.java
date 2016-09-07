/*
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.mskcc.cbio.model;

import java.io.Serializable;

/**
 *
 * @author ochoaa
 */
public class CopyNumberSegmentFile implements Serializable {
    
    public enum ReferenceGenomeId {
        
        hg18("hg18"),
        hg19("hg19");
        
        private String name;
        
        ReferenceGenomeId(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
    }
    
    private Integer segFileId;
    private Integer cancerStudyId;
    private ReferenceGenomeId referenceGenomeId;
    private String description;
    private String filename;
    
    /**
     * @return the segFileId
     */
    public Integer getSegFileId() {
        return segFileId;
    }

    /**
     * @param segFileId the segFileId to set
     */
    public void setSegFileId(Integer segFileId) {
        this.segFileId = segFileId;
    }

    /**
     * @return the cancerStudyId
     */
    public Integer getCancerStudyId() {
        return cancerStudyId;
    }

    /**
     * @param cancerStudyId the cancerStudyId to set
     */
    public void setCancerStudyId(Integer cancerStudyId) {
        this.cancerStudyId = cancerStudyId;
    }

    /**
     * @return the referenceGenomeId
     */
    public ReferenceGenomeId getReferenceGenomeId() {
        return referenceGenomeId;
    }

    /**
     * @param referenceGenomeId the referenceGenomeId to set
     */
    public void setReferenceGenomeId(ReferenceGenomeId referenceGenomeId) {
        this.referenceGenomeId = referenceGenomeId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
}
