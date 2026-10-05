# Proof of Concept Results

All acceptance tests successfully passed. Below are the details and findings.

## Acceptance Tests

### A. Jenkins Initialization and Discovery
**Status:** PASS
**Evidence:** Jenkins successfully started via Docker Compose. The `casc.yaml` Configuration-as-Code and Job DSL properly initialized the `ci-validation-poc` multibranch pipeline. The job immediately discovered the `main` and `sprint-1` branches.

### B. PR #1 (First Commit - Coverage Gate Failure)
**Status:** PASS
**Evidence:** 
- A PR was opened from `feature/Feature1` into `sprint-1`.
- Jenkins automatically polled and triggered the build.
- The unit tests passed successfully.
- `diff-cover` evaluated the coverage against `origin/sprint-1` and reported: `Failure. Coverage is below 90%` (actual coverage was 81% for the missing lines).
- The pipeline executed a hard failure.
- GitHub branch protection prevented merging because the status check failed.

### C. PR #1 (Second Commit - Coverage Gate Pass)
**Status:** PASS
**Evidence:** 
- Pushed the second commit adding negative/boundary tests to the same PR branch.
- Jenkins polled and triggered a new build.
- Tests passed.
- `diff-cover` reported: `Coverage: 100%`.
- The GitHub status check turned green (`continuous-integration/jenkins/pr-merge` -> Success).
- The PR was successfully merged.

### D. PR #2 (Failing Unit Test)
**Status:** PASS
**Evidence:** 
- Opened PR #2 containing a deliberately failing unit test (`deliberatelyFailingTest`).
- Jenkins triggered the build.
- The Maven build failed during the `Build and test` stage with a `MojoFailureException`.
- The pipeline successfully aborted. The console log explicitly reported: `Stage "New-code coverage gate" skipped due to earlier failure(s)`.

### E. PR #3 (README Update)
**Status:** PASS
**Evidence:** 
- Opened PR #3 with only changes to `README.md`.
- Jenkins triggered the build.
- `diff-cover` correctly detected that no source code lines were altered.
- The console log reported: `No lines with coverage information in this diff.`
- The build passed and the coverage gate did not throw an error.

---

## Technical Findings & Deviations from Assumptions

1. **GitHub Status-Check Name:**
   - **Hypothesis:** `continuous-integration/jenkins/pr-merge`
   - **Reality:** Exactly as hypothesized. The GitHub Branch Source plugin defaults to the `continuous-integration/jenkins/pr-merge` context when reporting status checks for PR merge revisions.

2. **Build Durations:**
   - Docker build & initialization: ~1-2 minutes.
   - PR builds: Extremely fast. Maven compilation and tests took ~3-5 seconds. `diff-cover` analysis took <2 seconds. Total pipeline time per PR was roughly 15-20 seconds.

3. **Problems Hit and Fixed:**
   - **Missing Target Branch Ref:** By default, Jenkins multibranch PR builds check out the merged PR revision, but the local git workspace doesn't have the `origin/sprint-1` branch readily available for `diff-cover` to compare against.
     - *Fix:* Added `sh "git fetch --no-tags origin +refs/heads/${CHANGE_TARGET}:refs/remotes/origin/${CHANGE_TARGET}"` before running `diff-cover`.
   - **File Encodings on Windows:** Using PowerShell `echo` or `>>` creates UTF-16LE files by default, which causes issues for files like `.gitignore`. 
     - *Fix:* Used standard UTF-8 file writing via the script tools.
   - **Jenkins Docker Plugin Warnings:** The `casc.yaml` needs to be placed securely so that it is processed during the Jenkins initialization process despite the `jenkins_home` volume overriding defaults.
     - *Fix:* Copied `casc.yaml` to `/usr/share/jenkins/ref/casc.yaml`, which the Jenkins entrypoint automatically applies to the volume securely.
   - **Crumb Issues:** Calling `/build` manually via API required CSRF crumbs.
     - *Fix:* Relied exclusively on the 1-minute SCM polling configured via Job DSL, which worked flawlessly without user intervention.
