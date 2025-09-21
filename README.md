# Library CLI (Java OOP)

This project is a console app that manages publications (books and magazines) and demonstrates:

- Array: repository uses a fixed-capacity `Publication[]`.
- Inheritance: abstract `Publication` with subclasses `Book` and `Magazine`.
- Polymorphism: `summarize()` is invoked on `Publication` and runs the concrete implementation.
- Menu with `Scanner`: input is read from the console in `Main`.
- Abstract class: `Publication`.
- Repository: `PublicationRepository` with create, read, search, and delete operations.
- Exceptions: `ValidationException`, `DuplicateException`, `RepositoryFullException`, `NotFoundException`.
- Interfaces and validations: `Identifiable`, `Summarizable`, `Validatable`, and `validate()` in the domain.

## Structure

- `org.example.domain`: `Publication` (abstract), `Book`, `Magazine` + interfaces `Identifiable`, `Summarizable`, `Validatable`.
- `org.example.repo`: `PublicationRepository` (fixed array, O(n) for search/removal).
- `org.example.exceptions`: custom checked exceptions.
- `org.example.Main`: CLI with a console menu.

## Run without Maven (macOS/Linux)

Requires Java 25 (or newer) available as `javac` and `java`.

```bash
# Compile
mkdir -p target/classes
javac -d target/classes $(find src/main/java -name "*.java")

# Run
java -cp target/classes org.example.Main
```

Example interactive input:

```
1
B1
El Quijote
Cervantes
863
2
M1
National Geographic
101
3
4
B1
5
M1
3
0
```

## Run with Maven (optional)

If you have Maven installed:

```bash
mvn -DskipTests package
java -cp target/classes org.example.Main
```

## Notes

- The repository uses a fixed-capacity array. You can adjust capacity in `Main` (`new PublicationRepository(100)`).
- Validations throw `ValidationException` (empty fields, positive integers, length checks). `PublicationRepository` may also throw `DuplicateException`, `RepositoryFullException`, `NotFoundException`.
- If you see accented characters not rendering correctly in the console, ensure the terminal uses UTF-8 (you can also run with `-Dfile.encoding=UTF-8`).
