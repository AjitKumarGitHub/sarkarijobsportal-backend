package com.marbel.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marbel.job.service.SitemapService;

@RestController
public class SitemapController {

	@Autowired
	private SitemapService sitemapService;

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public String sitemapIndex() {
        return sitemapService.generateSitemapIndex();
    }

    @GetMapping(value = "/jobs-sitemap.xml", produces = "application/xml")
    public String jobsSitemap() {
        return sitemapService.generateJobsSitemap();
    }

    @GetMapping(value = "/admit-sitemap.xml", produces = "application/xml")
    public String admitSitemap() {
        return sitemapService.generateAdmitSitemap();
    }

    @GetMapping(value = "/results-sitemap.xml", produces = "application/xml")
    public String resultsSitemap() {
        return sitemapService.generateResultsSitemap();
    }
    
    @GetMapping(value = "/blogs-sitemap.xml", produces = "application/xml")
    public String blogs() {
        return sitemapService.generateBlogSitemap();
    }

}
