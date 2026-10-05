# FIBI CI proof of concept

Shows the post-commit validation flow with free tools: JUnit 5, JaCoCo and diff-cover (new-code coverage gate).

## Branches in this repo
- `sprint-1`: the sprint (integration) branch. Contains legacy code with no tests, so module coverage stays low.
- `feature/Feature1`: branched from `sprint-1`. Commit 1 adds code with a happy-path test only. Commit 2 adds negative and boundary tests.

## Run it on your Git host
1. Create an empty repo, then: `git remote add origin <url> && git push -u origin --all`
2. Protect `sprint-1`: require a pull request, at least 1 approval, and the `validate` check to pass (GitLab: pipelines must succeed).
3. Open a PR `feature/Feature1` -> `sprint-1` with only commit 1 (push `feature/Feature1~1` as a branch, or reset and push). The gate fails and the merge button is blocked.
4. Push commit 2. The gate passes. Merge stays blocked until someone approves.
5. Approve and merge. The merged code is on `sprint-1`.

## Run it locally (needs Maven 3.9+ and Java 17+)
    mvn -B verify
    pip install diff-cover
    diff-cover target/site/jacoco/jacoco.xml --compare-branch=sprint-1 --fail-under=90

Change `NEW_CODE_THRESHOLD` in the workflow to experiment. Bitbucket and Jenkins run the same two commands in their own pipeline syntax.

This is a test change to verify the PR validation pipeline.
