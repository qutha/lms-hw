# CI/CD Configuration

This project uses GitHub Actions for Continuous Integration and Continuous Deployment.

## Workflows

### 1. CI (Continuous Integration)
**File**: `.github/workflows/ci.yml`

Triggers on:
- Push to `main` and `develop` branches
- Pull requests to `main` and `develop` branches

Actions:
- Runs on Ubuntu with PostgreSQL service
- Builds the project using Maven
- Runs all tests
- Packages the application
- Uploads artifacts
- Generates test reports

### 2. CD (Continuous Deployment)
**File**: `.github/workflows/cd.yml`

Triggers on:
- Push to `main` branch
- Git tags matching `v*.*.*` pattern

Actions:
- Builds the application
- Builds and pushes Docker image to Docker Hub
- Creates GitHub releases for tagged versions

### 3. Code Quality
**File**: `.github/workflows/code-quality.yml`

Triggers on:
- Push to `main` and `develop` branches
- Pull requests to `main` and `develop` branches

Actions:
- Runs SonarCloud analysis (optional)
- Checks code style
- Runs SpotBugs analysis

### 4. Dependency Review
**File**: `.github/workflows/dependency-review.yml`

Triggers on:
- Pull requests to `main` and `develop` branches

Actions:
- Reviews dependencies for security vulnerabilities
- Comments summary in PR

## Required GitHub Secrets

To use all workflows, configure these secrets in your GitHub repository:

### For CD (Docker deployment):
- `DOCKER_USERNAME` - Your Docker Hub username
- `DOCKER_PASSWORD` - Your Docker Hub password or access token

### For Code Quality (optional):
- `SONAR_TOKEN` - Your SonarCloud token
- `SONAR_PROJECT_KEY` - Your SonarCloud project key
- `SONAR_ORGANIZATION` - Your SonarCloud organization

## Setting up Secrets

1. Go to your GitHub repository
2. Navigate to: Settings → Secrets and variables → Actions
3. Click "New repository secret"
4. Add each required secret

## Dependabot

**File**: `.github/dependabot.yml`

Automatically creates PRs to update:
- Maven dependencies (weekly)
- Docker base images (weekly)
- GitHub Actions versions (weekly)

## Usage

### Automatic Triggers
All workflows run automatically based on their triggers.

### Manual Deployment
To manually deploy:
1. Create a git tag: `git tag v1.0.0`
2. Push the tag: `git push origin v1.0.0`
3. The CD workflow will build and deploy automatically

### Docker Image Tags
Docker images are tagged with:
- `latest` - Latest build from main branch
- `main-<sha>` - Specific commit from main
- `v1.0.0` - Semantic version tags
- `1.0` - Major.minor version

## Local Testing

To test the build locally before pushing:

```bash
# Build
./mvnw clean package

# Run tests
./mvnw test

# Build Docker image
docker build -t lms-app:local .

# Run with docker-compose
docker compose up
```

## Branch Strategy

Recommended branching strategy:
- `main` - Production-ready code (triggers CD)
- `develop` - Development branch (triggers CI)
- Feature branches - Create PRs to `develop`

## Troubleshooting

### CI Fails
1. Check test results in the Actions tab
2. Review the test report artifact
3. Ensure PostgreSQL service is properly configured

### CD Fails
1. Verify Docker Hub credentials are correct
2. Check if the Docker image name matches your repository
3. Ensure you have push permissions to Docker Hub

### Code Quality Fails
1. SonarCloud issues are non-blocking by default
2. Configure SonarCloud secrets to enable full analysis
3. Code style and SpotBugs issues are informational
