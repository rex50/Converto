# GitHub Actions CI/CD Setup Guide

Follow these steps to enable the CI/CD pipeline for your Converto project.

## Prerequisites
- GitHub repository: https://github.com/rex50/Converto
- Open Exchange API key (free from https://openexchangerates.org/signup/free)

## Step-by-Step Setup

### Step 1: Add GitHub Secret (Required)

1. Go to https://github.com/rex50/Converto/settings/secrets/actions
2. Click **"New repository secret"**
3. Add the following:
   - **Name**: `OPEN_EXCHANGE_API_KEY`
   - **Value**: Your API key from openexchangerates.org
4. Click **"Add secret"**

### Step 2: Enable Workflow Permissions

1. Go to https://github.com/rex50/Converto/settings/actions
2. Scroll down to **"Workflow permissions"**
3. Select **"Read and write permissions"**
4. Check ☑️ **"Allow GitHub Actions to create and approve pull requests"**
5. Click **"Save"**

### Step 3: Push Workflow Files

Commit and push the following files to your repository:
[Configuration file](.github/workflows/ci-cd.yml)

### Step 4: Verify Pipeline Execution

1. Go to https://github.com/rex50/Converto/actions
2. You should see a workflow run starting automatically
3. Click on the workflow to view progress
4. Wait for all steps to complete (approx. 3-5 minutes)

### Step 5: Check Release

1. Go to https://github.com/rex50/Converto/releases
2. You should see a new release created
3. The release includes:
   - Release notes with changelog
   - Downloadable APK file
   - Version information

## What Happens Next?

Every time you push to `main` branch:
1. ✅ Unit tests run automatically
2. 📦 Debug APK is built
3. 📝 Changelog is generated from commits
4. 🎉 New release is created with APK

### View Releases
https://github.com/rex50/Converto/releases

## Making New Releases

To create a new release:

1. Update version in `app/build.gradle`:
```gradle
versionCode 2
versionName "1.1"
```

2. Commit and push:
```bash
git add app/build.gradle
git commit -m "Bump version to 1.1"
git push origin main
```

3. Pipeline automatically creates release `v1.1-2`

## Advanced: Manual Workflow Trigger

To run the pipeline manually:
1. Go to https://github.com/rex50/Converto/actions
2. Click "Android CI/CD Pipeline" workflow
3. Click "Run workflow" button
4. Select branch and click "Run workflow"

