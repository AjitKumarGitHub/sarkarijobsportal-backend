package com.marbel.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marbel.job.pojo.JobDetails;
import com.marbel.job.pojo.Post;
import com.marbel.job.repositories.JobRepository;
import com.marbel.job.repositories.PostRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class SitemapService {

	@Autowired
    private JobRepository jobRepo;
	
	@Autowired
	private PostRepository postRepository;
	
    private final String domain = "https://sarkarijobsportal.com";

   

    // -------------------------------
    // MASTER SITEMAP INDEX
    // -------------------------------
    public String generateSitemapIndex() {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                    <sitemap>
                        <loc>""" + domain + "/jobs-sitemap.xml</loc>" + """
                    </sitemap>
                    <sitemap>
                        <loc>""" + domain + "/admit-sitemap.xml</loc>" + """
                    </sitemap>
                    <sitemap>
                        <loc>""" + domain + "/results-sitemap.xml</loc>" + """
                    </sitemap>
                    <sitemap>
                        <loc>""" + domain + "/blog-sitemap.xml</loc>" + """
                    </sitemap>
                </sitemapindex>
                """;
    }

    // -------------------------------
    // JOBS (Dynamic)
    // -------------------------------
    public String generateJobsSitemap() {

        StringBuilder xml = new StringBuilder();
        xml.append(""" 
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
        """);

        List<JobDetails> jobs = jobRepo.findAll();

        for (JobDetails job : jobs) {
            xml.append("  <url>\n");
            xml.append("    <loc>")
                    .append(domain)
                    .append("/jobs/")
                    .append(job.getSlug())
                    .append("/")
                    .append(job.getId())
                    .append("</loc>\n");

            // SAFE Date handling
            if (job.getDate() != null) {
                try {
                    LocalDate parsedDate = LocalDate.parse(
                            job.getDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    );
                    xml.append("    <lastmod>").append(parsedDate).append("</lastmod>\n");
                } catch (Exception ignore) {}
            }

            xml.append("  </url>\n");
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    // -------------------------------
    // ADMIT CARDS (Static List Page)
    // -------------------------------
    public String generateAdmitSitemap() {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                <url>
                    <loc>""" + domain + "/admit-cards</loc>" + """
                </url>
            </urlset>
        """;
    }

    // -------------------------------
    // RESULTS (Static List Page)
    // -------------------------------
    public String generateResultsSitemap() {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                <url>
                    <loc>""" + domain + "/results</loc>" + """
                </url>
            </urlset>
        """;
    }

    public String generateBlogSitemap() {

        StringBuilder xml = new StringBuilder();
        xml.append("""
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
                    xmlns:xhtml="http://www.w3.org/1999/xhtml">
        """);

        List<Post> posts = postRepository.findAll();

        // Group posts by groupId → then by language
        Map<String, Map<String, Post>> groupedPosts =
                posts.stream().collect(
                    Collectors.groupingBy(
                        Post::getGroupId,
                        Collectors.toMap(
                            Post::getLanguage,
                            p -> p,
                            (a, b) -> a
                        )
                    )
                );

        for (Map<String, Post> group : groupedPosts.values()) {

            Post en = group.get("en");
            Post hi = group.get("hi");

            // English must exist (main URL)
            if (en == null) continue;

            String enUrl = domain + "/blogs/" + en.getSlug() + "/" + en.getId();
            String hiUrl = hi != null
                    ? domain + "/hi/blogs/" + hi.getSlug() + "/" + hi.getId()
                    : null;

            xml.append("  <url>\n");
            xml.append("    <loc>").append(enUrl).append("</loc>\n");

            // hreflang EN
            xml.append("""
                <xhtml:link rel="alternate" hreflang="en-IN" href="%s"/>
            """.formatted(enUrl));

            // hreflang HI
            if (hiUrl != null) {
                xml.append("""
                    <xhtml:link rel="alternate" hreflang="hi-IN" href="%s"/>
                """.formatted(hiUrl));
            }

            // x-default
            xml.append("""
                <xhtml:link rel="alternate" hreflang="x-default" href="%s"/>
            """.formatted(enUrl));

            // lastmod (use latest update among EN/HI)
            Instant latestUpdate = en.getUpdatedAt();
            if (hi != null && hi.getUpdatedAt() != null &&
                hi.getUpdatedAt().isAfter(latestUpdate)) {
                latestUpdate = hi.getUpdatedAt();
            }

            if (latestUpdate != null) {
                LocalDate localDate = latestUpdate
                        .atZone(java.time.ZoneId.of("UTC"))
                        .toLocalDate();

                xml.append("    <lastmod>")
                   .append(localDate)
                   .append("</lastmod>\n");
            }

            xml.append("  </url>\n");
        }

        xml.append("</urlset>");
        return xml.toString();
    }

}
