# Site report placeholder

This Maven site report plugin will generate a placeholder for a report that gets generated later. In most cases, you will want to generate the report at the same time you build the Maven site, but if you can't do that, this plugin can help. Configure the plugin with the `<reportLocation>` and when you eventually provide the report at said location, your site will be correctly configured.

## Example usage

```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>uk.co.mikeberinger</groupId>
            <artifactId>site-report-placeholder</artifactId>
            <version>1.0.0</version>
            <configuration>
                <reportName>Allure</reportName>
                <reportDescription>Allure report published after our test suites have been run</reportDescription>
                <reportLocation>allure-results/index</reportLocation>
            </configuration>
        </plugin>
    </plugins>
</reporting>
```

## Options

| Name              | Description                                                                                                                                                                               | Type    | Required |
|-------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|----------|
| reportName        | The name of your report                                                                                                                                                                   | String  | Yes      |
| reportDescription | A short description of your report                                                                                                                                                        | String  | Yes      |
| reportLocation    | The relative location of your report. NB: do not include the file extension, the .html will be added                                                                                      | String  | Yes      |
| placeholderNoRedirect | By default, this plugin will create a placeholder page that will automatically redirect you to your `reportLocation` if it exists. Set this option to true if you do not want this functionality. | boolean | No       |
| skip              | Skip the plugin execution                                                                                                                                                                 | boolean | No       |