package de.andreassiegel;

import de.andreassiegel.constraint.ExclusiveField;
import de.andreassiegel.constraint.IdOrName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by as on 21.05.16.
 */
@Data
@AllArgsConstructor
@Builder
@ExclusiveField(fields = { "name", "id" })
@IdOrName
public class Entity
{
    private String name;
    private String id;
}
