package com.k2data.precast.entity.dto;


/**
 * @author tianhao
 * @date 2018-12-04
 */
public class UserDiagramDo {

    /**
     * 客户名称
     */
    private String customName;
    /**
     * 用气量比率
     */
    private Double gasUsedRadio;
    /**
     * 用气价格
     */
    private Double price;
    /**
     * 用气计划符合度
     */
    private Double gasPlanMatch;
    /**
     * 用气合同匹配度
     */
    private Double gasContractMatch;
    /**
     * 用气增长量
     */
    private Double gasUsedGrowth;
    /**
     * 用气增长率
     */
    private Double gasUsedGrowthRadio;
    /**
     * 可承受气价能力
     */
    private Double acceptablePrice;
    /**
     * 模型评分
     */
    private Double score;
    /**
     * 用户打分
     */
    private Double artificialScore;
    /**
     * 综合打分
     */
    private Double compositeScore;

    /**
     * 保供评分
     */
    private Double guaranteedSupplyScore;

    /**
     * 供大于求评分
     */
    private Double overFlowSupplyScore;

    /**
     * 供小于求评分
     */
    private Double insufficientSupplyScore;

    /**
     * 客户行业气价偏离系数
     */
    private Double divergeRadio;

    /**
     * 聚类类别
     */
    private Integer clusterCategory;
    /**
     * 客户行业标识
     */
    private String customIndustry;
    /**
     * 用气稳定性
     */
    private Double stabilization;
    /**
     * 温度变化对用气量变化的敏感系数
     */
    private Double temperatureInfluenceRadio;

    /**
     * 类别
     */
    private String category;

    /**
     * 生产发展评分
     */
    private Double developScore;

    public Double getGasUsedRadio() {
        return gasUsedRadio;
    }

    public void setGasUsedRadio(Double gasUsedRadio) {
        this.gasUsedRadio = gasUsedRadio;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getGasPlanMatch() {
        return gasPlanMatch;
    }

    public void setGasPlanMatch(Double gasPlanMatch) {
        this.gasPlanMatch = gasPlanMatch;
    }

    public Double getGasContractMatch() {
        return gasContractMatch;
    }

    public void setGasContractMatch(Double gasContractMatch) {
        this.gasContractMatch = gasContractMatch;
    }

    public Double getGasUsedGrowth() {
        return gasUsedGrowth;
    }

    public void setGasUsedGrowth(Double gasUsedGrowth) {
        this.gasUsedGrowth = gasUsedGrowth;
    }

    public Double getGasUsedGrowthRadio() {
        return gasUsedGrowthRadio;
    }

    public void setGasUsedGrowthRadio(Double gasUsedGrowthRadio) {
        this.gasUsedGrowthRadio = gasUsedGrowthRadio;
    }

    public Double getAcceptablePrice() {
        return acceptablePrice;
    }

    public void setAcceptablePrice(Double acceptablePrice) {
        this.acceptablePrice = acceptablePrice;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getDivergeRadio() {
        return divergeRadio;
    }

    public void setDivergeRadio(Double divergeRadio) {
        this.divergeRadio = divergeRadio;
    }

    public Integer getClusterCategory() {
        return clusterCategory;
    }

    public void setClusterCategory(Integer clusterCategory) {
        this.clusterCategory = clusterCategory;
    }

    public String getCustomIndustry() {
        return customIndustry;
    }

    public void setCustomIndustry(String customIndustry) {
        this.customIndustry = customIndustry;
    }


    public Double getStabilization() {
        return stabilization;
    }

    public void setStabilization(Double stabilization) {
        this.stabilization = stabilization;
    }

    public Double getTemperatureInfluenceRadio() {
        return temperatureInfluenceRadio;
    }

    public void setTemperatureInfluenceRadio(Double temperatureInfluenceRadio) {
        this.temperatureInfluenceRadio = temperatureInfluenceRadio;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getDevelopScore() {
        return developScore;
    }

    public void setDevelopScore(Double developScore) {
        this.developScore = developScore;
    }

    public Double getArtificialScore() {
        return artificialScore;
    }

    public void setArtificialScore(Double artificialScore) {
        this.artificialScore = artificialScore;
    }

    public Double getCompositeScore() {
        return compositeScore;
    }

    public void setCompositeScore(Double compositeScore) {
        this.compositeScore = compositeScore;
    }

    public Double getGuaranteedSupplyScore() {
        return guaranteedSupplyScore;
    }

    public void setGuaranteedSupplyScore(Double guaranteedSupplyScore) {
        this.guaranteedSupplyScore = guaranteedSupplyScore;
    }

    public Double getOverFlowSupplyScore() {
        return overFlowSupplyScore;
    }

    public void setOverFlowSupplyScore(Double overFlowSupplyScore) {
        this.overFlowSupplyScore = overFlowSupplyScore;
    }

    public Double getInsufficientSupplyScore() {
        return insufficientSupplyScore;
    }

    public void setInsufficientSupplyScore(Double insufficientSupplyScore) {
        this.insufficientSupplyScore = insufficientSupplyScore;
    }
}
