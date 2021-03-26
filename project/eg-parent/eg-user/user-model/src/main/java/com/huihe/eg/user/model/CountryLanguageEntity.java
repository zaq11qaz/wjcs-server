package com.huihe.eg.user.model;

import com.cy.framework.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="国家语言列表",description="国家语言列表属性说明")
public class CountryLanguageEntity extends PageInfo {
    private static final long serialVersionUID = -556581666181353549L;
    /**
     * 
     */
    @ApiModelProperty(value="",example="1")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(value="国家")
    private String country;

    /**
     * 
     */
    @ApiModelProperty(value="语言")
    private String language;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return country 
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country 
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 
     * @return language 
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language 
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }
}