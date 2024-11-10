<p align="center">
    <img src="https://raw.githubusercontent.com/PKief/vscode-material-icon-theme/ec559a9f6bfd399b82bb44393651661b08aaf7ba/icons/folder-markdown-open.svg" align="center" width="30%">
</p>
<p align="center"><h1 align="center">FILEUTILS</h1></p>
<p align="center">
	<em>Empowering file management with precision and ease.</em>
</p>
<p align="center">
	<img src="https://img.shields.io/github/license/cyrus07424/fileUtils?style=default&logo=opensourceinitiative&logoColor=white&color=0080ff" alt="license">
	<img src="https://img.shields.io/github/last-commit/cyrus07424/fileUtils?style=default&logo=git&logoColor=white&color=0080ff" alt="last-commit">
	<img src="https://img.shields.io/github/languages/top/cyrus07424/fileUtils?style=default&color=0080ff" alt="repo-top-language">
	<img src="https://img.shields.io/github/languages/count/cyrus07424/fileUtils?style=default&color=0080ff" alt="repo-language-count">
</p>
<p align="center"><!-- default option, no dependency badges. -->
</p>
<p align="center">
	<!-- default option, no dependency badges. -->
</p>
<br>

##  Table of Contents

- [ Overview](#-overview)
- [ Features](#-features)
- [ Project Structure](#-project-structure)
  - [ Project Index](#-project-index)
- [ Getting Started](#-getting-started)
  - [ Prerequisites](#-prerequisites)
  - [ Installation](#-installation)
  - [ Usage](#-usage)
  - [ Testing](#-testing)
- [ Project Roadmap](#-project-roadmap)
- [ Contributing](#-contributing)
- [ License](#-license)
- [ Acknowledgments](#-acknowledgments)

---

##  Overview

**fileUtils Project Overview:

**Solving the Chaos of File Management

**Key Features:** Simplify file operations, extract metadata, bulk rename, organize MP3 files, and automate dependency updates and Java builds.

**Benefits:** Enhance file processing efficiency, streamline organization, and automate routine tasks for developers and project maintainers.

**Target Audience:** Developers seeking streamlined file handling and automation in Java projects.

---

##  Features

|      | Feature         | Summary       |
| :--- | :---:           | :---          |
| ⚙️  | **Architecture**  | <ul><li>Centralizes environment configurations in `Configurations.java` for consistent settings across modules.</li><li>Enables metadata extraction from image files using `MetadataExtractorHelper.java` and `TikaHelper.java`.</li><li>Facilitates bulk renaming and GPS coordinate extraction in `AdvancedBulkRename.java` and `ExtractGpsCoordinates.java`.</li></ul> |
| 🔩 | **Code Quality**  | <ul><li>Well-structured code files with clear functionalities and responsibilities.</li><li>Utilizes utility functions for specific tasks, enhancing code readability and maintainability.</li><li>Follows Java coding conventions and best practices.</li></ul> |
| 📄 | **Documentation** | <ul><li>Comprehensive documentation in Java files for understanding key functionalities.</li><li>Automated dependency updates and Maven build workflows documented in `.github` files.</li><li>Clear explanations of file processing capabilities and directory organization.</li></ul> |
| 🔌 | **Integrations**  | <ul><li>Automated dependency updates using `dependabot.yml` for Maven and GitHub Actions.</li><li>CI/CD workflows automated with Maven using GitHub Actions in `maven.yml`.</li><li>Integration of Apache Tika library for file content extraction.</li></ul> |
| 🧩 | **Modularity**    | <ul><li>Modular design with separate files for specific functionalities like metadata extraction, bulk renaming, and GPS coordinate extraction.</li><li>Encapsulates related functionalities within individual classes for better organization.</li><li>Promotes reusability and extensibility of code components.</li></ul> |
| 🧪 | **Testing**       | <ul><li>Testing details not explicitly mentioned in the provided context.</li><li>Recommendation: Implement unit tests for critical functionalities to ensure code reliability and robustness.</li><li>Consider incorporating testing frameworks like JUnit for automated testing.</li></ul> |
| ⚡️  | **Performance**   | <ul><li>Efficient file processing capabilities for metadata extraction, bulk renaming, and GPS coordinate extraction.</li><li>Optimized code for handling large volumes of files within specified directories.</li><li>Performance enhancements through streamlined directory organization and metadata extraction processes.</li></ul> |
| 🛡️ | **Security**      | <ul><li>Security details not explicitly mentioned in the provided context.</li><li>Recommendation: Implement secure coding practices to prevent vulnerabilities like directory traversal attacks.</li><li>Ensure input validation and sanitization to mitigate potential security risks.</li></ul> |
| 📦 | **Dependencies**  | <ul><li>Dependencies managed through `dependabot.yml` for automated updates.</li><li>Utilizes Java and Maven for project development and build processes.</li><li>Relies on Apache Tika library for content extraction functionalities.</li></ul> |

---

##  Project Structure

```sh
└── fileUtils/
    ├── .github
    │   ├── dependabot.yml
    │   └── workflows
    ├── LICENSE
    ├── README.md
    ├── pom.xml
    └── src
        └── main
```


###  Project Index
<details open>
	<summary><b><code>FILEUTILS/</code></b></summary>
	<details> <!-- __root__ Submodule -->
		<summary><b>__root__</b></summary>
		<blockquote>
			<table>
			</table>
		</blockquote>
	</details>
	<details> <!-- src Submodule -->
		<summary><b>src</b></summary>
		<blockquote>
			<details>
				<summary><b>main</b></summary>
				<blockquote>
					<details>
						<summary><b>java</b></summary>
						<blockquote>
							<details>
								<summary><b>constants</b></summary>
								<blockquote>
									<table>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/constants/Configurations.java'>Configurations.java</a></b></td>
										<td>- Defines environment configurations for the project, ensuring consistent settings across modules<br>- This interface centralizes key parameters for easy access and modification, promoting maintainability and scalability in the codebase architecture.</td>
									</tr>
									</table>
								</blockquote>
							</details>
							<details>
								<summary><b>utils</b></summary>
								<blockquote>
									<table>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/utils/MetadataExtractorHelper.java'>MetadataExtractorHelper.java</a></b></td>
										<td>- Enables extraction of metadata from image files, including retrieving all EXIF tags, orientation, timezone, capture date, and GPS information<br>- The code provides methods to access and process various metadata details for a given file, contributing to comprehensive data extraction capabilities within the project's architecture.</td>
									</tr>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/utils/TikaHelper.java'>TikaHelper.java</a></b></td>
										<td>- Provides utility functions to extract content type, metadata, and file extension based on file input using Apache Tika library<br>- The `getContentType` method detects the content type of a file, while `getMetadata` retrieves metadata<br>- `getExtention` maps content types to file extensions<br>- These functions enhance file processing capabilities within the project architecture.</td>
									</tr>
									</table>
								</blockquote>
							</details>
							<details>
								<summary><b>mains</b></summary>
								<blockquote>
									<table>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/mains/AdvancedBulkRename.java'>AdvancedBulkRename.java</a></b></td>
										<td>- The code file `AdvancedBulkRename.java` facilitates bulk renaming of files within a specified directory<br>- It iterates through files, extracts metadata, and generates new filenames based on timestamps or parent directory names<br>- The script offers options for recursive processing and customization of the renaming process.</td>
									</tr>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/mains/ExtractGpsCoordinates.java'>ExtractGpsCoordinates.java</a></b></td>
										<td>- ExtractGpsCoordinates.java processes directories and files to extract GPS coordinates from images' EXIF data<br>- It recursively navigates directories, reads EXIF tags, and retrieves latitude and longitude information<br>- This functionality aids in geolocating images within the project's architecture.</td>
									</tr>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/mains/Mp3FileGroupingDirectory.java'>Mp3FileGroupingDirectory.java</a></b></td>
										<td>- Reorganizes MP3 files based on album names, creating directories for each album and moving files accordingly<br>- Parses audio file tags to extract album information for directory structuring<br>- Handles various exceptions during the process.</td>
									</tr>
									<tr>
										<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/src/main/java/mains/RemoveDirectorySuffix.java'>RemoveDirectorySuffix.java</a></b></td>
										<td>- The code file `RemoveDirectorySuffix.java` integrates directories by removing specified suffixes from their names<br>- It processes subdirectories within a target directory, moving files to new directories with modified names<br>- This functionality streamlines directory organization by eliminating specific suffix patterns.</td>
									</tr>
									</table>
								</blockquote>
							</details>
						</blockquote>
					</details>
				</blockquote>
			</details>
		</blockquote>
	</details>
	<details> <!-- .github Submodule -->
		<summary><b>.github</b></summary>
		<blockquote>
			<table>
			<tr>
				<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/.github/dependabot.yml'>dependabot.yml</a></b></td>
				<td>- Automates daily dependency updates for Maven and GitHub Actions, ensuring project dependencies are regularly checked and updated within set limits<br>- Scheduled to run daily at midnight in the Asia/Tokyo timezone, limiting open pull requests to 10 for each ecosystem.</td>
			</tr>
			</table>
			<details>
				<summary><b>workflows</b></summary>
				<blockquote>
					<table>
					<tr>
						<td><b><a href='https://github.com/cyrus07424/fileUtils/blob/master/.github/workflows/maven.yml'>maven.yml</a></b></td>
						<td>- Automate Java project builds with Maven using GitHub Actions<br>- Ensure JDK 8 setup, build with Maven, and add labels based on pull request events<br>- Optimize CI/CD workflows for efficient project development and collaboration.</td>
					</tr>
					</table>
				</blockquote>
			</details>
		</blockquote>
	</details>
</details>

---
##  Getting Started

###  Prerequisites

Before getting started with fileUtils, ensure your runtime environment meets the following requirements:

- **Programming Language:** Java


###  Installation

Install fileUtils using one of the following methods:

**Build from source:**

1. Clone the fileUtils repository:
```sh
❯ git clone https://github.com/cyrus07424/fileUtils
```

2. Navigate to the project directory:
```sh
❯ cd fileUtils
```

3. Install the project dependencies:

echo 'INSERT-INSTALL-COMMAND-HERE'



###  Usage
Run fileUtils using the following command:
echo 'INSERT-RUN-COMMAND-HERE'

###  Testing
Run the test suite using the following command:
echo 'INSERT-TEST-COMMAND-HERE'

---
##  Project Roadmap

- [X] **`Task 1`**: <strike>Implement feature one.</strike>
- [ ] **`Task 2`**: Implement feature two.
- [ ] **`Task 3`**: Implement feature three.

---

##  Contributing

- **💬 [Join the Discussions](https://github.com/cyrus07424/fileUtils/discussions)**: Share your insights, provide feedback, or ask questions.
- **🐛 [Report Issues](https://github.com/cyrus07424/fileUtils/issues)**: Submit bugs found or log feature requests for the `fileUtils` project.
- **💡 [Submit Pull Requests](https://github.com/cyrus07424/fileUtils/blob/main/CONTRIBUTING.md)**: Review open PRs, and submit your own PRs.

<details closed>
<summary>Contributing Guidelines</summary>

1. **Fork the Repository**: Start by forking the project repository to your github account.
2. **Clone Locally**: Clone the forked repository to your local machine using a git client.
   ```sh
   git clone https://github.com/cyrus07424/fileUtils
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to github**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.
8. **Review**: Once your PR is reviewed and approved, it will be merged into the main branch. Congratulations on your contribution!
</details>

<details closed>
<summary>Contributor Graph</summary>
<br>
<p align="left">
   <a href="https://github.com{/cyrus07424/fileUtils/}graphs/contributors">
      <img src="https://contrib.rocks/image?repo=cyrus07424/fileUtils">
   </a>
</p>
</details>

---

##  License

This project is protected under the [MIT License](https://choosealicense.com/licenses/mit/) License. For more details, refer to the [LICENSE](https://github.com/cyrus07424/fileUtils/blob/master/LICENSE) file.

---

##  Acknowledgments

- List any resources, contributors, inspiration, etc. here.

---
