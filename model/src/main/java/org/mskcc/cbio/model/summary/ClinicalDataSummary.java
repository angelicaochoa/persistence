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

package org.mskcc.cbio.model.summary;

import java.io.Serializable;

/**
 *
 * @author ochoaa
 */
public abstract class ClinicalDataSummary implements Serializable {
    
    private Integer internalId;
    private String attrId;
    private String attrValue;

    /**
     * @return the internalId
     */
    public Integer getInternalId() {
        return internalId;
    }

    /**
     * @param internalId the internalId to set
     */
    public void setInternalId(Integer internalId) {
        this.internalId = internalId;
    }

    /**
     * @return the attrId
     */
    public String getAttrId() {
        return attrId;
    }

    /**
     * @param attrId the attrId to set
     */
    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    /**
     * @return the attrValue
     */
    public String getAttrValue() {
        return attrValue;
    }

    /**
     * @param attrValue the attrValue to set
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
    
}
