# Changelog

All notable changes to this project will be documented in this file.

The format is based on Keep a Changelog and this project adheres to Semantic Versioning.

## [Unreleased]

## [0.1.0] - 2025-09-21
### Added
- Initial CLI app for Library domain using Java OOP.
- Abstract class `Publication` and subclasses `Book` and `Magazine`.
- Interfaces: `Identifiable`, `Summarizable`, `Validatable`.
- Repository: `PublicationRepository` backed by a fixed-size array.
- Custom exceptions: `ValidationException`, `DuplicateException`, `RepositoryFullException`, `NotFoundException`.
- Console menu using `Scanner` with input validations.
- README with run instructions (javac and optional Maven).

### Changed
- README translated to English.

### Notes
- If characters with accents appear incorrectly in the console, ensure UTF-8 encoding or run with `-Dfile.encoding=UTF-8`.

