package com.google.cloud.tools.jib.maven.skaffold;

import com.google.cloud.tools.jib.maven.JibPluginConfiguration;
import com.google.cloud.tools.jib.maven.MavenProjectProperties;
import com.google.cloud.tools.jib.maven.MavenRawConfiguration;
import com.google.cloud.tools.jib.plugins.common.PluginConfigurationProcessor;
import com.google.common.annotations.VisibleForTesting;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(
    name = SyncMap.GOAL_NAME,
    requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME,
    aggregator = true)
public class SyncMap extends JibPluginConfiguration {

  @VisibleForTesting static final String GOAL_NAME = "_skaffold-sync-map";

  @Override
  public void execute() throws MojoExecutionException {

    MavenProjectProperties projectProperties =
        MavenProjectProperties.getForProject(getProject(), getSession(), getLog());

    MavenRawConfiguration configuration = new MavenRawConfiguration(this);

    try {
      String syncMapJson =
          PluginConfigurationProcessor.getSkaffoldSyncMap(configuration, projectProperties);
      System.out.println(syncMapJson);
    } catch (Exception e) {
      throw new MojoExecutionException("Failed to generate Jib file map", e);
    }
  }
}
