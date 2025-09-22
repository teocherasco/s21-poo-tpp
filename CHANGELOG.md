# Changelog

All notable changes to this project will be documented in this file.

The format is based on Keep a Changelog and this project adheres to Semantic Versioning.

## [Unreleased]

***## [0.2.0] - 2025-09-22
### Changed
- Domain migrated from Library to Ice Cream Shop focusing on order intake.
- CLI rewritten to manage Customers and Orders with a Scanner-driven menu.
- Abstract class `MenuItem` introduced with polymorphic subclasses `IceCreamCup` and `Milkshake`.
- Repositories now injected via `Repository<T>` interface; in-memory implementations use `ArrayList`.

### Added
- Domain entities: `Customer`, `Order`, `OrderLine` with composition/aggregation/association.
- Enums: `Flavor`, `Topping`, `CupSize`, `OrderStatus`.
- Validations across the domain, `totalPrice()` logic, and summaries for display.
- README updated to English and to reflect the new domain and run instructions.

### Notes
- Legacy Library classes remain in the codebase for reference but are not used by the current CLI.

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
