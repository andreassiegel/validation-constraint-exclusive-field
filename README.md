# Exclusive Field Validation Constraint

This project provides two class-level annotations to be used with `javax.validation` that are capable of checking identifier fields to verify that only one of possibly alternative fields is defined.

For instance, you might have an object that can either be identified by its name or by another ID, but you don't want both fields to be set in order to avoid potential conflicts that could cause problems in further processing.

### @IdOrName

The first implementation variant is closely related to the outlined use case and assumes `id` and `name` fields to be existent.

For validation, two different constraint validator implementations are provided:

- **Entity Validator:** very closely related to the `Entity` type object to be validated and directly calls the getter methods
- **Reflection Validator:** validates `Object` type objects and access the fields via reflection

### @ExclusiveField

The second implementation variant is similar to the Reflection Validator, but provides more flexibility, because it does not expect any particular field to be existent.

Instead, the constraint annotation takes an array of field names as parameter:

```@ExclusiveField(fields = { "name", "id" })```

Validation then uses reflection to access those fields and verifies that only one of them is actually set.

Note that all of the following is considered as *undefined*:

- `field == null`
- `field.isEmpty()` (if `field` is instance of `String`)
- `field.toString().isEmpty()`
