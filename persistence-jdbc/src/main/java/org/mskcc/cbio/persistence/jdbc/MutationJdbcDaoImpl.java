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

package org.mskcc.cbio.persistence.jdbc;

import org.mskcc.cbio.model.*;

import java.util.*;
import java.sql.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of MutationJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class MutationJdbcDaoImpl implements MutationJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(MutationJdbcDaoImpl.class);
    
    @Autowired
    public MutationJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public void addMutation(Mutation mutation) {
        String SQL = "INSERT INTO mutation " +
                "(mutation_event_id, genetic_profile_id, sample_id, entrez_gene_id, center, sequencer, " + 
                "mutation_status, validation_status, tumor_seq_allele1, tumor_seq_allele2, matched_norm_sample_barcode, " +
                "match_norm_seq_allele1, match_norm_seq_allele2, tumor_validation_allele1, " +
                "tumor_validation_allele2, match_norm_validation_allele1, match_norm_validation_allele2, " +
                "verification_status, sequencing_phase, sequence_source, validation_method, score, bam_file, " + 
                "tumor_alt_count, tumor_ref_count, normal_alt_count, normal_ref_count, amino_acid_change) " +
                "VALUES (:mutation_event_id, :genetic_profile_id, :sample_id, :entrez_gene_id, :center, :sequencer, " + 
                ":mutation_status, :validation_status, :tumor_seq_allele1, :tumor_seq_allele2, :matched_norm_sample_barcode, " +
                ":match_norm_seq_allele1, :match_norm_seq_allele2, :tumor_validation_allele1, " +
                ":tumor_validation_allele2, :match_norm_validation_allele1, :match_norm_validation_allele2, " +
                ":verification_status, :sequencing_phase, :sequence_source, :validation_method, :score, :bam_file, " + 
                ":tumor_alt_count, :tumor_ref_count, :normal_alt_count, :normal_ref_count, :amino_acid_change)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("mutation_event_id", mutation.getMutationEventId());
        namedParameters.addValue("genetic_profile_id", mutation.getGeneticProfileId());
        namedParameters.addValue("sample_id", mutation.getSampleId());
        namedParameters.addValue("entrez_gene_id", mutation.getEntrezGeneId());
        namedParameters.addValue("center", mutation.getCenter());
        namedParameters.addValue("sequencer", mutation.getSequencer());
        namedParameters.addValue("mutation_status", mutation.getMutationStatus());
        namedParameters.addValue("validation_status", mutation.getValidationStatus());
        namedParameters.addValue("tumor_seq_allele1", mutation.getTumorSeqAllele1());
        namedParameters.addValue("tumor_seq_allele2", mutation.getTumorSeqAllele2());
        namedParameters.addValue("matched_norm_sample_barcode", mutation.getMatchedNormSampleBarcode());
        namedParameters.addValue("match_norm_seq_allele1", mutation.getMatchNormSeqAllele1());
        namedParameters.addValue("match_norm_seq_allele2", mutation.getMatchNormSeqAllele1());
        namedParameters.addValue("tumor_validation_allele1", mutation.getTumorValidationAllele1());
        namedParameters.addValue("tumor_validation_allele2", mutation.getTumorValidationAllele1());
        namedParameters.addValue("match_norm_validation_allele1", mutation.getMatchNormValidationAllele1());
        namedParameters.addValue("match_norm_validation_allele2", mutation.getMatchNormValidationAllele1());
        namedParameters.addValue("verification_status", mutation.getVerificationStatus());
        namedParameters.addValue("sequencing_phase", mutation.getSequencingPhase());
        namedParameters.addValue("sequence_source", mutation.getSequenceSource());
        namedParameters.addValue("validation_method", mutation.getValidationMethod());
        namedParameters.addValue("score", mutation.getScore());
        namedParameters.addValue("bam_file", mutation.getBamFile());
        namedParameters.addValue("tumor_alt_count", mutation.getTumorAltCount());
        namedParameters.addValue("tumor_ref_count", mutation.getTumorRefCount());
        namedParameters.addValue("normal_alt_count", mutation.getNormalAltCount());
        namedParameters.addValue("normal_ref_count", mutation.getNormalRefCount());
        namedParameters.addValue("amino_acid_change", mutation.getAminoAcidChange());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch(DataAccessException ex) {
            LOG.error("Error importing mutation record into MUTATION");
            ex.printStackTrace();
        }
    }
    
    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public void addMutationEvent(MutationEvent mutationEvent) {
        String SQL = "INSERT INTO mutation_event " + 
                "(mutation_event_id, entrez_gene_id, chr, start_position, end_position, reference_allele, tumor_seq_allele, " + 
                "protein_change, mutation_type, functional_impact_score, fis_value, link_xvar, link_pdb, link_msa, " + 
                "ncbi_build, strand, variant_type, db_snp_rs, db_snp_val_status, oncotator_dbsnp_rs, oncotator_refseq_mrna_id, " + 
                "oncotator_codon_change, oncotator_uniprot_entry_name, oncotator_uniprot_accession, " + 
                "oncotator_protein_pos_start, oncotator_protein_pos_end, canonical_transcript, keyword)" +
                "VALUES (:mutation_event_id, :entrez_gene_id, :chr, :start_position, :end_position, :reference_allele, :tumor_seq_allele, " + 
                ":protein_change, :mutation_type, :functional_impact_score, :fis_value, :link_xvar, :link_pdb, :link_msa, " + 
                ":ncbi_build, :strand, :variant_type, :db_snp_rs, :db_snp_val_status, :oncotator_dbsnp_rs, :oncotator_refseq_mrna_id, " + 
                ":oncotator_codon_change, :oncotator_uniprot_entry_name, :oncotator_uniprot_accession, " + 
                ":oncotator_protein_pos_start, :oncotator_protein_pos_end, :canonical_transcript, :keyword)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("mutation_event_id", mutationEvent.getMutationEventId());
        namedParameters.addValue("entrez_gene_id", mutationEvent.getEntrezGeneId());
        namedParameters.addValue("chr", mutationEvent.getChr());
        namedParameters.addValue("start_position", mutationEvent.getStartPosition());
        namedParameters.addValue("end_position", mutationEvent.getEndPosition());
        namedParameters.addValue("reference_allele", mutationEvent.getReferenceAllele());
        namedParameters.addValue("tumor_seq_allele", mutationEvent.getTumorSeqAllele());
        namedParameters.addValue("protein_change", mutationEvent.getProteinChange());
        namedParameters.addValue("mutation_type", mutationEvent.getMutationType());
        namedParameters.addValue("functional_impact_score", mutationEvent.getFunctionalImpactScore());
        namedParameters.addValue("fis_value", mutationEvent.getFisValue());
        namedParameters.addValue("link_xvar", mutationEvent.getLinkXvar());
        namedParameters.addValue("link_pdb", mutationEvent.getLinkPdb());
        namedParameters.addValue("link_msa", mutationEvent.getLinkMsa());
        namedParameters.addValue("ncbi_build", mutationEvent.getNcbiBuild());
        namedParameters.addValue("strand", mutationEvent.getStrand());
        namedParameters.addValue("variant_type", mutationEvent.getVariantType());
        namedParameters.addValue("db_snp_rs", mutationEvent.getDbSnpRs());
        namedParameters.addValue("db_snp_val_status", mutationEvent.getDbSnpValStatus());
        namedParameters.addValue("oncotator_dbsnp_rs", mutationEvent.getOncotatorDbsnpRs());
        namedParameters.addValue("oncotator_refseq_mrna_id", mutationEvent.getOncotatorRefseqMrnaId());
        namedParameters.addValue("oncotator_codon_change", mutationEvent.getOncotatorCodonChange());
        namedParameters.addValue("oncotator_uniprot_entry_name", mutationEvent.getOncotatorUniprotEntryName());
        namedParameters.addValue("oncotator_uniprot_accession", mutationEvent.getOncotatorUniprotAccession());
        namedParameters.addValue("oncotator_protein_pos_start", mutationEvent.getOncotatorProteinPosStart());
        namedParameters.addValue("oncotator_protein_pos_end", mutationEvent.getOncotatorProteinPosEnd());
        namedParameters.addValue("canonical_transcript", mutationEvent.getCanonicalTranscript());
        namedParameters.addValue("keyword", mutationEvent.getKeyword());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch(DataAccessException ex) {
            LOG.error("Error importing mutation event record into MUTATION_EVENT");
            ex.printStackTrace();
        }
    }

    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public void updateMutation(Mutation mutation) {
        String SQL = "UPDATE mutation " +
                "SET center = :center, sequencer = :sequencer, mutation_status = :mutation_status, " + 
                "validation_status = :validation_status, tumor_seq_allele1 = :tumor_seq_allele1, " + 
                "tumor_seq_allele2 = :tumor_seq_allele2, matched_norm_sample_barcode = :matched_norm_sample_barcode, " +
                "match_norm_seq_allele1 = :match_norm_seq_allele1, match_norm_seq_allele2 = :match_norm_seq_allele2, " +
                "tumor_validation_allele1 = :tumor_validation_allele1, tumor_validation_allele2 = :tumor_validation_allele2, " + 
                "match_norm_validation_allele1 = :match_norm_validation_allele1, match_norm_validation_allele2 = :match_norm_validation_allele2, " +
                "verification_status = :verification_status, sequencing_phase = :sequencing_phase, " + 
                "sequence_source = :sequence_source, validation_method = :validation_method, score = :score, bam_file = :bam_file, " + 
                "tumor_alt_count = :tumor_alt_count, tumor_ref_count = :tumor_ref_count, normal_alt_count = :normal_alt_count, " + 
                "normal_ref_count = :normal_ref_count, amino_acid_change = :amino_acid_change " + 
                "WHERE mutation_event_id = :mutation_event_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("mutation_event_id", mutation.getMutationEventId());
        namedParameters.addValue("center", mutation.getCenter());
        namedParameters.addValue("sequencer", mutation.getSequencer());
        namedParameters.addValue("mutation_status", mutation.getMutationStatus());
        namedParameters.addValue("validation_status", mutation.getValidationStatus());
        namedParameters.addValue("tumor_seq_allele1", mutation.getTumorSeqAllele1());
        namedParameters.addValue("tumor_seq_allele2", mutation.getTumorSeqAllele2());
        namedParameters.addValue("matched_norm_sample_barcode", mutation.getMatchedNormSampleBarcode());
        namedParameters.addValue("match_norm_seq_allele1", mutation.getMatchNormSeqAllele1());
        namedParameters.addValue("match_norm_seq_allele2", mutation.getMatchNormSeqAllele1());
        namedParameters.addValue("tumor_validation_allele1", mutation.getTumorValidationAllele1());
        namedParameters.addValue("tumor_validation_allele2", mutation.getTumorValidationAllele1());
        namedParameters.addValue("match_norm_validation_allele1", mutation.getMatchNormValidationAllele1());
        namedParameters.addValue("match_norm_validation_allele2", mutation.getMatchNormValidationAllele1());
        namedParameters.addValue("verification_status", mutation.getVerificationStatus());
        namedParameters.addValue("sequencing_phase", mutation.getSequencingPhase());
        namedParameters.addValue("sequence_source", mutation.getSequenceSource());
        namedParameters.addValue("validation_method", mutation.getValidationMethod());
        namedParameters.addValue("score", mutation.getScore());
        namedParameters.addValue("bam_file", mutation.getBamFile());
        namedParameters.addValue("tumor_alt_count", mutation.getTumorAltCount());
        namedParameters.addValue("tumor_ref_count", mutation.getTumorRefCount());
        namedParameters.addValue("normal_alt_count", mutation.getNormalAltCount());
        namedParameters.addValue("normal_ref_count", mutation.getNormalRefCount());
        namedParameters.addValue("amino_acid_change", mutation.getAminoAcidChange());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (Exception ex) {
            LOG.error("Error updating mutation record");
            ex.printStackTrace();
        }
    }
    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public Mutation getMutation(Mutation mutation) {
        String SQL = "SELECT " +
                "mutation.mutation_event_id AS 'mutation.mutationEventId', " +
                "mutation.genetic_profile_id AS 'mutation.geneticProfileId', " +
                "mutation.sample_id AS 'mutation.sampleId', " +
                "mutation.entrez_gene_id AS 'mutation.entrezGeneId', " +
                "mutation.center AS 'mutation.center', " +
                "mutation.sequencer AS 'mutation.sequencer', " +
                "mutation.mutation_status AS 'mutation.mutationStatus', " +
                "mutation.validation_status AS 'mutation.validationStatus', " +
                "mutation.tumor_seq_allele1 AS 'mutation.tumorSeqAllele1', " +
                "mutation.tumor_seq_allele2 AS 'mutation.tumorSeqAllele2', " +
                "mutation.matched_norm_sample_barcode AS 'mutation.matchedNormSampleBarcode', " +
                "mutation.match_norm_seq_allele1 AS 'mutation.matchNormSeqAllele1', " +
                "mutation.match_norm_seq_allele2 AS 'mutation.matchNormSeqAllele2', " +
                "mutation.tumor_validation_allele1 AS 'mutation.tumorValidationAllele1', " +
                "mutation.tumor_validation_allele2 AS 'mutation.tumorValidationAllele2', " +
                "mutation.match_norm_validation_allele1 AS 'mutation.matchNormValidationAllele1', " +
                "mutation.match_norm_validation_allele2 AS 'mutation.matchNormValidationAllele2', " +
                "mutation.verification_status AS 'mutation.verificationStatus', " +
                "mutation.sequencing_phase AS 'mutation.sequencingPhase', " +
                "mutation.sequence_source AS 'mutation.sequenceSource', " +
                "mutation.validation_method AS 'mutation.validationMethod', " +
                "mutation.score AS 'mutation.score', " +
                "mutation.bam_file AS 'mutation.bamFile', " +
                "mutation.tumor_alt_count AS 'mutation.tumorAltCount', " +
                "mutation.tumor_ref_count AS 'mutation.tumorRefCount', " +
                "mutation.normal_alt_count AS 'mutation.normalAltCount', " +
                "mutation.normal_ref_count AS 'mutation.normalRefCount', " +
                "mutation.amino_acid_change AS 'mutation.aminoAcidChange', " +
                "mutation_event.mutation_event_id AS 'mutation_event.mutationEventId', " +
                "mutation_event.entrez_gene_id AS 'mutation_event.entrezGeneId', " +
                "mutation_event.chr AS 'mutation_event.chr', " +
                "mutation_event.start_position AS 'mutation_event.startPosition', " +
                "mutation_event.end_position AS 'mutation_event.endPosition', " +
                "mutation_event.reference_allele AS 'mutation_event.referenceAllele', " +
                "mutation_event.tumor_seq_allele AS 'mutation_event.tumorSeqAllele', " +
                "mutation_event.protein_change AS 'mutation_event.proteinChange', " +
                "mutation_event.mutation_type AS 'mutation_event.mutationType', " +
                "mutation_event.functional_impact_score AS 'mutation_event.functionalImpactScore', " +
                "mutation_event.fis_value AS 'mutation_event.fisValue', " +
                "mutation_event.link_xvar AS 'mutation_event.linkXvar', " +
                "mutation_event.link_pdb AS 'mutation_event.linkPdb', " +
                "mutation_event.link_msa AS 'mutation_event.linkMsa', " +
                "mutation_event.ncbi_build AS 'mutation_event.ncbiBuild', " +
                "mutation_event.strand AS 'mutation_event.strand', " +
                "mutation_event.variant_type AS 'mutation_event.variantType', " +
                "mutation_event.db_snp_rs AS 'mutation_event.dbSnpRs', " +
                "mutation_event.db_snp_val_status AS 'mutation_event.dbSnpValStatus', " +
                "mutation_event.oncotator_dbsnp_rs AS 'mutation_event.oncotatorDbsnpRs', " +
                "mutation_event.oncotator_refseq_mrna_id AS 'mutation_event.oncotatorRefseqMrnaId', " +
                "mutation_event.oncotator_codon_change AS 'mutation_event.oncotatorCodonChange', " +
                "mutation_event.oncotator_uniprot_entry_name AS 'mutation_event.oncotatorUniprotEntryName', " +
                "mutation_event.oncotator_uniprot_accession AS 'mutation_event.oncotatorUniprotAccession', " +
                "mutation_event.oncotator_protein_pos_start AS 'mutation_event.oncotatorProteinPosStart', " +
                "mutation_event.oncotator_protein_pos_end AS 'mutation_event.oncotatorProteinPosEnd', " +
                "mutation_event.canonical_transcript AS 'mutation_event.canonicalTranscript', " +
                "mutation_event.keyword AS 'mutation_event.keyword' " +
                "FROM mutation " +
                "INNER JOIN mutation_event ON mutation_event.mutation_event_id = mutation.mutation_event_id " + 
                "WHERE mutation.genetic_profile_id = :genetic_profile_id " +
                "AND mutation.sample_id = :sample_id " +
                "AND mutation_event.entrez_gene_id = :entrez_gene_id " + 
                "AND mutation_event.chr = :chr " + 
                "AND mutation_event.start_position = :start_position " + 
                "AND mutation_event.end_position = :end_position " +
                "AND mutation_event.tumor_seq_allele = :tumor_seq_allele " +                
                "AND mutation_event.protein_change = :protein_change " +
                "AND mutation_event.mutation_type = :mutation_type";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genetic_profile_id", mutation.getGeneticProfileId());
        namedParameters.addValue("sample_id", mutation.getSampleId());
        namedParameters.addValue("entrez_gene_id", mutation.getMutationEvent().getEntrezGeneId());
        namedParameters.addValue("chr", mutation.getMutationEvent().getChr());
        namedParameters.addValue("start_position", mutation.getMutationEvent().getStartPosition());
        namedParameters.addValue("end_position", mutation.getMutationEvent().getEndPosition());
        namedParameters.addValue("tumor_seq_allele", mutation.getMutationEvent().getTumorSeqAllele());
        namedParameters.addValue("protein_change", mutation.getMutationEvent().getProteinChange());
        namedParameters.addValue("mutation_type", mutation.getMutationEvent().getMutationType());
        
        Mutation existingMutation = null;
        try {
            existingMutation = (Mutation) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, mutationRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}        
        
        return existingMutation;
    }
    
    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public MutationEvent getMutationEvent(MutationEvent mutationEvent) {
        String SQL = "SELECT * FROM mutation_event " + 
                "WHERE entrez_gene_id = :entrez_gene_id " + 
                "AND chr = :chr " + 
                "AND start_position = :start_position " + 
                "AND end_position = :end_position " +
                "AND tumor_seq_allele = :tumor_seq_allele " +                
                "AND protein_change = :protein_change " +
                "AND mutation_type = :mutation_type";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("entrez_gene_id", mutationEvent.getEntrezGeneId());
        namedParameters.addValue("chr", mutationEvent.getChr());
        namedParameters.addValue("start_position", mutationEvent.getStartPosition());
        namedParameters.addValue("end_position", mutationEvent.getEndPosition());
        namedParameters.addValue("tumor_seq_allele", mutationEvent.getTumorSeqAllele());
        namedParameters.addValue("protein_change", mutationEvent.getProteinChange());
        namedParameters.addValue("mutation_type", mutationEvent.getMutationType());
        
        MutationEvent existingMutationEvent = null;
        try {
            existingMutationEvent = (MutationEvent) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, new BeanPropertyRowMapper(MutationEvent.class));
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return existingMutationEvent;
    }
    
    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public Integer calculateMutationCount(Integer geneticProfileId) {
        String SQL = "INSERT INTO mutation_count " +
                "SELECT genetic_profile.genetic_profile_id, sample_id, COUNT(*) AS mutation_count " +
                "FROM mutation, genetic_profile " +
                "WHERE mutation.genetic_profile_id = genetic_profile.genetic_profile_id " +
                "AND genetic_profile.genetic_profile_id= :genetic_profile_id " +
                "GROUP BY genetic_profile.genetic_profile_id , SAMPLE_ID";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("genetic_profile_id", geneticProfileId);
        
        int rows = 0;
        try {
            rows = namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (Exception ex) {
            LOG.error("Error calculating mutation count");
            ex.printStackTrace();
        }
        
        return rows;
    }
    
    /**
     * Implementation of {@code MutationJdbcDao}
     */
    @Override
    public Integer getLargestMutationEventId() {
        String SQL = "SELECT MAX(mutation_event_id) FROM mutation_event";
        
        Integer mutationEventId = -1;
        try {
            mutationEventId = namedParameterJdbcTemplate.query(SQL, (ResultSet rs, int i) -> rs.getInt(1)).get(0);
        }
        catch (DataAccessException ex) {}
        
        return mutationEventId;
    }
    
    /**
     * Mutation row mapper.
     * 
     * @return RowMapper
     */
    private RowMapper mutationRowMapper() {
        return (RowMapper) (ResultSet rs, int i) -> {
            HashMap<String, BeanMap> beans_by_name = new HashMap();
            beans_by_name.put("mutation", BeanMap.create(new Mutation()));
            beans_by_name.put("mutation_event", BeanMap.create(new MutationEvent()));

            // go through each column from sql query and set respective bean properties accordingly
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int index=1; index<=resultSetMetaData.getColumnCount(); index++) {
                // get the table name and the field name for the bean
                String table = resultSetMetaData.getColumnLabel(index).split("\\.")[0];
                String field = resultSetMetaData.getColumnLabel(index).split("\\.")[1];

                BeanMap beanMap = beans_by_name.get(table);
                if (rs.getObject(index) != null) {
                    beanMap.put(field, rs.getObject(index));
                }
            }
            Mutation mutation = (Mutation) beans_by_name.get("mutation").getBean();
            mutation.setMutationEvent((MutationEvent) beans_by_name.get("mutation_event").getBean());
            
            return mutation;
            };
            
        }

}
