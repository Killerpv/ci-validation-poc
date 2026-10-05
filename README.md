# CI Validation Proof of Concept

This repository demonstrates an end-to-end PR-validation CI flow using Jenkins and GitHub.

## Overview
When a pull request is opened against a target branch (e.g. `sprint-1`), Jenkins automatically:
1. Compiles the code and runs JUnit 5 unit tests with JaCoCo.
2. Runs a "new-code coverage gate" using `diff-cover`.
3. Reports the status back to GitHub.

GitHub is configured to require this check to pass before merging is allowed.

## Setup Instructions

### 1. Prerequisites
- Docker and Docker Compose installed.
- A GitHub Personal Access Token (PAT) with `repo` scope.

### 2. Configuration
Create a `.env` file in the root of the project with the following variables:
```
ADMIN_PASSWORD=admin
GITHUB_USERNAME=your_username
GITHUB_TOKEN=ghp_your_token_here
GITHUB_OWNER=your_org_or_username
GITHUB_REPO=ci-validation-poc
```
(You can copy `.env.example` as a template).

### 3. Run Jenkins
Start the Jenkins Docker container:
```bash
docker compose up -d --build
```
Jenkins will be available at `http://localhost:8080`.

### 4. How it works
- **Configuration as Code**: Jenkins is configured entirely via code (`jenkins/casc.yaml` and `jenkins/Dockerfile`).
- **Job DSL**: The multibranch pipeline is automatically created on startup.
- **Polling**: Jenkins polls GitHub every 1 minute. No webhooks or public URLs are needed.
- **Pipeline**: The pipeline is defined in `Jenkinsfile.pr`.
