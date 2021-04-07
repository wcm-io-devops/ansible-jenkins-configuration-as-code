[![CI](https://github.com/wcm-io-devops/ansible-jenkins-configuration-as-code/workflows/CI/badge.svg?branch=master&event=push)](https://github.com/wcm-io-devops/ansible-jenkins-configuration-as-code/actions?query=workflow%3ACI)

# wcm_io_devops.jenkins_configuration_as_code

This role manages the installation, configuration and update of the
[Jenkins Configuration as Code Plugin aka JCasC](https://github.com/jenkinsci/configuration-as-code-plugin).

Tasks:
* Install JCasC plugin(s)
* Configure the path for the JCasC plugin
* Deployment of JCasC configuration files
* Reloading of JCasC

## Requirements

This role requires Ansible 2.7 or higher and a running Jenkins on the
target instance.

## Role Variables

Available variables are listed below, along with their default values.

    jenkins_casc_admin_username: admin

Jenkins admin username.

    jenkins_casc_admin_password: admin

Jenkins admin password.

    jenkins_casc_owner: jenkins

Linux jenkins user.

    jenkins_casc_group: "{{ jenkins_casc_owner }}"

Linux group of jenkins user.

    jenkins_casc_jenkins_hostname: localhost

Hostname of the jenkins instance.

    jenkins_casc_jenkins_port: 8080

HTTP port of the jenkins instance.

    jenkins_casc_jenkins_url_prefix: ""

Url prefix of the jenkins instance, e.g. when running in tomcat.

    jenkins_casc_jenkins_base_url: "http://{{ jenkins_casc_jenkins_hostname }}:{{ jenkins_casc_jenkins_port }}{{ jenkins_casc_jenkins_url_prefix }}"

The base url of the jenkins instance.

    jenkins_casc_jenkins_home: "/var/lib/jenkins"

Path to the jenkins casc directory containing the yaml files for configuration.

    jenkins_casc_config_path: "{{ jenkins_casc_jenkins_home }}/jcasc"

Path to the jenkins casc directory containing the yaml files for configuration.

    jenkins_casc_config_path_configure: false

Enables / Disabling the configuration of the configuration path. When
this value is set to true the JcasC path in the Jenkins instance is set
to `jenkins_casc_config_path`. When the path differs from the previous
set path the configuration is directly reloaded.

    jenkins_casc_config_fileglobs: []

Config files/templates to upload. When the result of this step is
changed the configuration will be reloaded.

    jenkins_casc_config_unmanaged_delete: false

Controls if files that existing files in the 'jenkins_casc_config_path' are deleted when they are not included in the 'jenkins_casc_config_fileglobs'.
Deletion will only be executed when at least one file was uploaded to the 'jenkins_casc_config_path'.

    jenkins_casc_script_timeout: 60

The timeout for jenkins_script tasks in seconds.

    jenkins_casc_plugins_present:
      - name: configuration-as-code
        version: "1.3"
      - name: configuration-as-code-support
        version: "1.3"

Plugins needed for configuration-as-code.

    jenkins_casc_secrets_dir: /var/jenkins_secrets

Folder where the credentials will be stored on the master.
Path will be configured in environment variable SECRETS.

    jenkins_casc_secrets_unmanaged_delete: true

Controls if the role will delete existing but not defined credentials from jenkins_casc_secrets_dir.
Deletion will only be executed when at least one secret was uploaded to the 'jenkins_casc_secrets_dir'.

    jenkins_casc_secrets: []
    # Example:
    # - id: credential-id
    #   value: credential-value

List of id/value credential pairs. The `id` can then be referenced in jcasc with `${id}` as value reference.

## Dependencies

This role depends on the
[wcm_io_devops.jenkins_plugins](https://github.com/wcm-io-devops/ansible-jenkins-plugins)
role to install/uninstall the plugins.

As transitive dependency this role uses the
[wcm_io_devops.jenkins_facts](https://github.com/wcm-io-devops/ansible-jenkins-facts)
role to retrieve the list of installed plugins from the Jenkins
instance.

## Example Playbook

This playbook will set the JCasC configuration path to
`/var/lib/jenkins/jcasc-folder` and will deploy all `.yml` and
`.yaml` files found below `file/jcasc/my-jenkins/` to this folder.

    - name: "Deploy jcasc"
      hosts: jenkins
      vars:
        jenkins_casc_config_path_configure: true
        jenkins_casc_config_path: "/var/lib/jenkins/jcasc-folder"
        jenkins_casc_config_fileglobs:
            - file/jcasc/my-jenkins/*.yml
            - file/jcasc/my-jenkins/*.yaml
      roles:
        - wcm_io_devops.jenkins_configuration_as_code

## License

Apache 2.0
