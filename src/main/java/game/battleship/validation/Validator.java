package game.battleship.validation;

import game.battleship.model.validation.ValidationResult;

public interface Validator<T> {
    ValidationResult validate(T input);
}