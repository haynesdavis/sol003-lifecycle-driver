# Releasing the Driver

The following guide details the steps for releasing the SOL003 Lifecycle Driver. This may only be performed by a user with admin rights to this Git repository and the `icr.io/cp4na-drivers` IBM Cloud Container Registry.

## 1. Ensure Milestone

Ensure there is a milestone created for the release at: [https://github.com/IBM/sol003-lifecycle-driver/milestones](https://github.com/IBM/sol003-lifecycle-driver/milestones).

Also ensure all issues going into this release are assigned to this milestone. **Move any issues from unreleased milestones into this release if the code has been merged**

## 2. Update version (on develop)

Ensure the version in `pom.xml` starts with the corresponding version for the release.

For example, if releasing `0.2.7`, ensure the `pom.xml` contains:

```
<version>0.2.7-SNAPSHOT</version>
```

This should have been done after the last release but it's good to check as the planned version may have changed (we expected to release a patch `0.2.7` but due to the nature of changes we've decided it's a minor release `0.3.0` instead)

Commit and push these changes.

## 3. Update CHANGELOG (on develop)

Update the `CHANGELOG.md` file with a list of issues fixed by this release (see other items in this file to get an idea of the desired format).

Commit and push these changes.

## 4. Merge Develop to Master

Development work is normally carried out on the `develop` branch. Merge this branch to `master`, by creating a PR.

Then perform the release from the `master` branch. This ensures the `master` branch is tagged correctly.

> Note: do NOT delete the `develop` branch

## 5. Build and Release (on master)

Access the `sol003-lifecycle-driver` build job on the internal CI/CD tool (maintainers should be aware and have access to this. Speak to another maintainer if not).

Navigate to the job for the `master` branch. The merge to `master` should have already triggered a build. Let this build complete in order to verify there were no issues with the merge. The automated job will not complete the release, it will only build and run the unit tests.

Once ready, click `Build with Parameters` on the `master` branch job. Enable the `release` option and click `BUILD`.

Wait for the build to complete successfully.

## 6. Verify Release

Verify the CI/CD job has created a [release on Github](https://github.com/IBM/sol003-lifecycle-driver/releases).

Ensure the tag, title and changelog are all correct. Also ensure the helm chart `tgz` file has been attached.

Verify the release has been published to [icr](icr.io/cp4na-drivers).

## 7. Cleanup

- Close the Milestone for this release on [Github](https://github.com/IBM/sol003-lifecycle-driver/milestones)
- Create a new Milestone for next release (if one does not exist).

# Manual Approach

**Please use the instructions above. The manual approach is now legacy and only kept for the rare circumstances**

## 1-4. Prepare Release

Complete steps 1-4 from the main release instructions (found above).

## 5. Build and Release (on master)  

> Note: Make sure to pull-in the latest and correct tag required for the openjdk image locally before preparing the release build.  
> e.g  
> You can find the openjdk image details here: https://github.com/IBM/sol003-lifecycle-driver/blob/master/src/main/resources/docker/Dockerfile#L1-L2  
> `docker pull openjdk:8u302-jre`

Run the following command (the `dev` profile ensures extra log statements are available in the built code):
```
./mvnw clean package -Pdev,docker,helm
```

This should produce 2 artifacts:
- a locally built docker image, e.g. `icr.io/cp4na-drivers/sol003-lifecycle-driver:0.2.0`
- a helm chart, e.g. `sol003-lifecycle-driver-0.2.0.tgz`

Verify the docker image has been produced by running
```
docker image ls
```

Verify that a helm chart is built in the `target/helm/repo` directory, e.g.
```
ls target/helm/repo
```

## 6. Release artifacts

The Docker image not been pushed by the previous build step so must be done manually, e.g.
```
echo <IAMAPIKEY> | docker login --username iamapikey --password-stdin icr.io/cp4na-drivers/
docker push icr.io/cp4na-drivers/sol003-lifecycle-driver:0.2.0
```

Complete the following:

- Visit the [releases](https://github.com/IBM/sol003-lifecycle-driver/releases) section of the driver repository
- Click `Draft a new release`
- Input the version the `--version` option earlier as the tag e.g. 0.2.0
- Use the same value for the `Release title` e.g. 0.2.0
- Add release notes in the description of the release. Look at previous releases to see the format. Usually, we will list the issues fixed.
- Attach the Helm chart `tgz` file produced by `mvnw` command in the `target/helm/repo` directory

## 7. Cleanup

Complete step 7 from the main release instructions (found above).
