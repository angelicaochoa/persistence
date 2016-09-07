package org.mskcc.cbio.persistence.mybatis;

import org.apache.ibatis.annotations.Param;
import org.mskcc.cbio.model.Mutation;
import org.mskcc.cbio.model.MutationCount;
import org.mskcc.cbio.persistence.dto.AltCount;
import org.mskcc.cbio.persistence.dto.KeywordSampleCount;
import org.mskcc.cbio.persistence.dto.MutatedGeneSampleCount;
import org.mskcc.cbio.persistence.dto.SignificantlyMutatedGene;

import java.util.List;

public interface MutationMapper {

    List<Mutation> getMutationsDetailed(@Param("geneticProfileStableIds") List<String> geneticProfileStableIds,
                                        @Param("hugoGeneSymbols") List<String> hugoGeneSymbols,
                                        @Param("sampleStableIds") List<String> sampleStableIds,
                                        @Param("sampleListStableId") String sampleListStableId);

    List<AltCount> getMutationsCounts(@Param("type") String type, @Param("hugoGeneSymbol") String hugoGeneSymbol,
                                      @Param("start") Integer start, @Param("end") Integer end,
                                      @Param("cancerStudyIdentifiers") List<String> cancerStudyIdentifiers,
                                      @Param("perStudy") Boolean perStudy);

    List<Mutation> getMutations(@Param("sampleIds") List<Integer> sampleIds,
                                @Param("entrezGeneIds") List<Integer> entrezGeneIds,
                                @Param("geneticProfileId") Integer geneticProfileId,
                                @Param("simplified") Boolean simplified);

    Boolean hasAlleleFrequencyData(@Param("geneticProfileId") Integer geneticProfileId,
                                   @Param("sampleId") Integer sampleId);

    void groupConcatMaxLenSet();

    List<SignificantlyMutatedGene> getSignificantlyMutatedGenes(@Param("geneticProfileId") Integer geneticProfileId,
                                                                @Param("entrezGeneIds") List<Integer> entrezGeneIds,
                                                                @Param("sampleIds") List<Integer> sampleIds,
                                                                @Param("thresholdRecurrence") Integer thresholdRecurrence,
                                                                @Param("thresholdNumGenes") Integer thresholdNumGenes);

    List<MutationCount> countMutationEvents(@Param("geneticProfileId") Integer geneticProfileId,
                                            @Param("sampleIds") List<Integer> sampleIds);

    List<MutatedGeneSampleCount> countSamplesWithMutatedGenes(@Param("geneticProfileId") Integer geneticProfileId,
                                                              @Param("entrezGeneIds") List<Integer> entrezGeneIds);

    List<KeywordSampleCount> countSamplesWithKeywords(@Param("geneticProfileId") Integer geneticProfileId,
                                                      @Param("keywords") List<String> keywords);

    List<Integer> getGenesOfMutations(@Param("mutationEventIds") List<Integer> mutationEventIds);

    List<String> getKeywordsOfMutations(@Param("mutationEventIds") List<Integer> mutationEventIds);
}
