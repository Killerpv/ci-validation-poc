FROM jenkins/jenkins:lts-jdk17

USER root

RUN apt-get update && \
    apt-get install -y maven git python3 python3-venv && \
    rm -rf /var/lib/apt/lists/*

RUN python3 -m venv /opt/venv && \
    /opt/venv/bin/pip install --upgrade pip && \
    /opt/venv/bin/pip install diff-cover && \
    chown -R jenkins:jenkins /opt/venv

USER jenkins

ENV PATH="/opt/venv/bin:$PATH"

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

ENV CASC_JENKINS_CONFIG="/usr/share/jenkins/ref/casc.yaml"
COPY casc.yaml /usr/share/jenkins/ref/casc.yaml
