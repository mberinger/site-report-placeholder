# Site report placeholder

This Maven site report plugin will generate a placeholder for a report that gets generated later. In most cases, you will want to generate the report at the same time you build the Maven site, but if you can't do that, this plugin can help. Configure the plugin with the `<reportLocation>` and when you eventually provide the report at said location, your site will be correctly configured.

## Example usage

```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>uk.co.mikeberinger</groupId>
            <artifactId>site-report-placeholder</artifactId>
            <version>1.0.2</version>
            <configuration>
                <reportName>Allure</reportName>
                <reportDescription>Allure report published after our test suites have been run</reportDescription>
                <reportLocation>allure-results/index</reportLocation>
            </configuration>
        </plugin>
    </plugins>
</reporting>
```

If you have a set of reports:

```xml
<plugin>
    <groupId>uk.co.mikeberinger</groupId>
    <artifactId>site-report-placeholder</artifactId>
    <version>1.0.2</version>
    <reportSets>
        <reportSet>
            <id>existing-report-scenario-1</id>
            <configuration>
                <reportName>Existing report scenario 1</reportName>
                <reportDescription>Placeholder loads report within iframe</reportDescription>
                <reportLocation>existing-report/index</reportLocation>
            </configuration>
            <reports>
                <report>site-report-placeholder</report>
            </reports>
        </reportSet>
        <reportSet>
            <id>existing-report-scenario-2</id>
            <configuration>
                <reportName>Existing report scenario 2</reportName>
                <reportDescription>Placeholder redirects to existing report</reportDescription>
                <reportLocation>existing-report/index</reportLocation>
                <redirectNoIframe>true</redirectNoIframe>
            </configuration>
            <reports>
                <report>site-report-placeholder</report>
            </reports>
        </reportSet>
        <reportSet>
            <id>existing-report-scenario-3</id>
            <configuration>
                <reportName>Existing report scenario 3</reportName>
                <reportDescription>Report already exists so no placeholder is created</reportDescription>
                <reportLocation>existing-report/index</reportLocation>
                <placeholderNoRedirect>true</placeholderNoRedirect>
            </configuration>
            <reports>
                <report>site-report-placeholder</report>
            </reports>
        </reportSet>
        <reportSet>
            <id>missing-report-scenario</id>
            <configuration>
                <reportName>Missing report scenario</reportName>
                <reportDescription>Report not available yet so placeholder created</reportDescription>
                <reportLocation>missing-report</reportLocation>
                <placeholderNoRedirect>true</placeholderNoRedirect>
            </configuration>
            <reports>
                <report>site-report-placeholder</report>
            </reports>
        </reportSet>
    </reportSets>
</plugin>
```

## Options

| Name                  | Description                                                                                                                                                                                                                                                                                                                                              | Type    | Required |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|----------|
| reportName            | The name of your report                                                                                                                                                                                                                                                                                                                                  | String  | Yes      |
| reportDescription     | A short description of your report                                                                                                                                                                                                                                                                                                                       | String  | Yes      |
| reportLocation        | The relative location of your report. NB: do not include the file extension, the .html will be added                                                                                                                                                                                                                                                     | String  | Yes      |
| placeholderNoRedirect | By default, this plugin will create a placeholder page that will automatically redirect you to your `reportLocation` if it exists. Set this option to true if you do not want this functionality.                                                                                                                                                        | boolean | No       |
| redirectNoIframe      | By default, this plugin will create a placeholder page that will automatically redirect you to your `reportLocation` if it exists. To help keep your site layout consistent (such as the menu), when your report become available, the placeholder will load the report within an iframe. Set this option to true if you do not want this functionality. | boolean | No       |
| skip                  | Skip the plugin execution                                                                                                                                                                                                                                                                                                                                | boolean | No       |