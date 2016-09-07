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
public class TypeOfCancer implements Serializable {

    private String typeOfCancerId;
    private String name;
    private String clinicalTrialKeywords;
    private String dedicatedColor;
    private String shortName;
    private String parent;

    /**
     * @return the typeOfCancerId
     */
    public String getTypeOfCancerId() {
        return typeOfCancerId;
    }

    /**
     * @param typeOfCancerId the typeOfCancerId to set
     */
    public void setTypeOfCancerId(String typeOfCancerId) {
        this.typeOfCancerId = typeOfCancerId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the clinicalTrialKeywords
     */
    public String getClinicalTrialKeywords() {
        return clinicalTrialKeywords;
    }

    /**
     * @param clinicalTrialKeywords the clinicalTrialKeywords to set
     */
    public void setClinicalTrialKeywords(String clinicalTrialKeywords) {
        this.clinicalTrialKeywords = clinicalTrialKeywords;
    }

    /**
     * @return the dedicatedColor
     */
    public String getDedicatedColor() {
        return dedicatedColor;
    }

    /**
     * @param dedicatedColor the dedicatedColor to set
     */
    public void setDedicatedColor(String dedicatedColor) {
        this.dedicatedColor = dedicatedColor;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }
   
}