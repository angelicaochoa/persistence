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

import org.mskcc.cbio.model.summary.GeneSummary;

import java.util.List;
import java.util.regex.*;

/**
 *
 * @author ochoaa
 */
public class Gene extends GeneSummary {

    private List<String> aliases;

    /**
     * @return the aliases
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * @param aliases the aliases to set
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
    
    /**
     * @return the chromosome
     */
    public String getChromosome() {

        if (getCytoband() == null) {
            return null;
        }
        if (getCytoband().toUpperCase().startsWith("X")) {
            return "X";
        }
        if (getCytoband().toUpperCase().startsWith("Y")) {
            return "Y";
        }

        Pattern p = Pattern.compile("([0-9]+).*");
        Matcher m = p.matcher(getCytoband());
        if (m.find()) {
            return m.group(1);
        }

        return null;
    }

}
