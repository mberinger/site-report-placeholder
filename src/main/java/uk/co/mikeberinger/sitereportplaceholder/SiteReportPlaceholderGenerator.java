package uk.co.mikeberinger.sitereportplaceholder;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;

import javax.inject.Inject;
import java.util.Locale;
import java.util.UUID;

import static java.lang.String.format;

/**
 * Generates a placeholder for a site report.
 */
@Mojo(
    name = "site-report-placeholder",
    defaultPhase = LifecyclePhase.SITE,
    requiresDependencyResolution = ResolutionScope.RUNTIME,
    requiresProject = true,
    threadSafe = true
)
public class SiteReportPlaceholderGenerator extends AbstractMavenReport {

    private String placeholderFilename = "site-report-placeholder";

    /**
     * The name of your report
     */
    @Parameter( property = "reportName", required = true )
    private String reportName;

    /**
     * A short description of your report
     */
    @Parameter( property = "reportDescription", required = true )
    private String reportDescription;

    /**
     * The relative location of your report. NB: do not include the file extension, the .html will be added
     */
    @Parameter( property = "reportLocation", required = true )
    private String reportLocation;

    /**
     * By default, this plugin will create a placeholder page that will automatically redirect you to your {@link #reportLocation} if it exists. Set this option to true if you do not want this functionality.
     */
    @Parameter( property = "placeholderNoRedirect" )
    private boolean placeholderNoRedirect;

    /**
     * Skip the plugin execution
     */
    @Parameter( property = "skip" )
    private boolean skip;

    @Inject
    public SiteReportPlaceholderGenerator(MavenProject mavenProject) {
        ReportPlugin thisPlugin = mavenProject.getReporting()
                .getReportPluginsAsMap()
                .get("uk.co.mikeberinger:site-report-placeholder");
        // if the plugin has multiple report sets configured, append a unique suffix to avoid filename clash
        if (!thisPlugin.getReportSets()
                .isEmpty()) {
            placeholderFilename += "-" + UUID.randomUUID();
        }
    }

    public String getOutputName() {
        // the page that is linked to from project-reports.html
        return placeholderNoRedirect ? reportLocation : placeholderFilename;
    }

    public String getName(Locale locale) {
        // Name of the report when listed in the project-reports.html page of a project
        return reportName;
    }

    public String getDescription(Locale locale) {
        // Description of the report when listed in the project-reports.html page of a project
        return reportDescription;
    }

    @Override
    public boolean canGenerateReport() {
        return !skip;
    }

    @Override
    protected void executeReport(Locale locale) {
        Log logger = getLog();

        logger.info(format("Generating %s.html for %s %s", getOutputName(), project.getName(), project.getVersion()));

        Sink mainSink = getSink();

        mainSink.head();
        mainSink.title();
        mainSink.text(format("Placeholder project report for %s %s", project.getName(), project.getVersion()));
        mainSink.title_();
        mainSink.head_();

        mainSink.body();

        String reportLocationPage = reportLocation + ".html";

        if (!placeholderNoRedirect) {
            // loader icon & message
            mainSink.rawText("<style>\n");
            mainSink.rawText("\t.loader {\n");
            mainSink.rawText("\t\tdisplay: inline-block;\n");
            mainSink.rawText("\t\tborder: 5px solid #f3f3f3;\n");
            mainSink.rawText("\t\tborder-radius: 50%;\n");
            mainSink.rawText("\t\tborder-top: 5px solid #3498db;\n");
            mainSink.rawText("\t\twidth: 10px;\n");
            mainSink.rawText("\t\theight: 10px;\n");
            mainSink.rawText("\t\t-webkit-animation: spin 2s linear infinite; /* Safari */\n");
            mainSink.rawText("\t\tanimation: spin 2s linear infinite;\n");
            mainSink.rawText("\t}\n");
            mainSink.rawText("\t@-webkit-keyframes spin {\n");
            mainSink.rawText("\t\t0% { -webkit-transform: rotate(0deg); }\n");
            mainSink.rawText("\t\t100% { -webkit-transform: rotate(360deg); }\n");
            mainSink.rawText("\t}\n");
            mainSink.rawText("\t@keyframes spin {\n");
            mainSink.rawText("\t\t0% { transform: rotate(0deg); }\n");
            mainSink.rawText("\t\t100% { transform: rotate(360deg); }\n");
            mainSink.rawText("\t}\n");
            mainSink.rawText("</style>\n");

            // script to redirect to report if it exists
            mainSink.rawText("<script>\n");
            mainSink.rawText("\twindow.onload = function() {\n");
            mainSink.rawText("\t\tvar request = new XMLHttpRequest;\n");
            // perform synchronous check for existence of report
            mainSink.rawText(format("\t\trequest.open('GET', '%s', false);%n", reportLocationPage));
            mainSink.rawText("\t\trequest.send();\n");
            mainSink.rawText(format("\t\tif (request.status != 404) window.location.href = '%s';%n", reportLocationPage));
            mainSink.rawText("\t\tdocument.getElementById('reportExistsCheck').style.display = 'none';\n");
            mainSink.rawText("\t}\n");
            mainSink.rawText("</script>\n");
        }

        mainSink.section1();
        mainSink.sectionTitle1();
        mainSink.text(format("Placeholder project report for %s %s", project.getName(), project.getVersion()));
        mainSink.sectionTitle1_();

        mainSink.paragraph();
        String placeholderReplacementInfo = placeholderNoRedirect ? "It will be manually replaced later." : format("It will automatically redirect you to '%s.html' when it exists.", reportLocation);
        mainSink.text("This placeholder was generated by the site-report-placeholder plugin. " + placeholderReplacementInfo);
        if (!placeholderNoRedirect) {
            mainSink.rawText("<div id='reportExistsCheck'>\n");
            mainSink.rawText("\t<div class='loader'></div>\n");
            mainSink.rawText("\t<span>Checking if report exists...</span>\n");
            mainSink.rawText("</div>\n");
        }
        mainSink.paragraph_();

        mainSink.section1_();
        mainSink.body_();
    }
}
