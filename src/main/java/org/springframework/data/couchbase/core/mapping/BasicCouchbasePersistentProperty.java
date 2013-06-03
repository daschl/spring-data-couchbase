/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.couchbase.core.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.util.StringUtils;

/**
 * Implements annotated property representations of a given Field instance.
 *
 * This object is used to gather information out of properties on objects
 * that need to be persisted. For example, it supports overriding of the
 * actual property name by providing custom annotations.
 *
 * @author Michael Nitschinger
 */
public class BasicCouchbasePersistentProperty
  extends AnnotationBasedPersistentProperty<CouchbasePersistentProperty>
  implements CouchbasePersistentProperty {

  /**
   * Create a new instance of the BasicCouchbasePersistentProperty class.
   *
   * @param field the field of the original reflection.
   * @param propertyDescriptor the PropertyDescriptor.
   * @param owner the original owner of the property.
   * @param simpleTypeHolder the type holder.
   */
  public BasicCouchbasePersistentProperty(Field field,
    PropertyDescriptor propertyDescriptor, CouchbasePersistentEntity<?> owner,
    SimpleTypeHolder simpleTypeHolder) {
    super(field, propertyDescriptor, owner, simpleTypeHolder);
  }

  /**
   * Creates a new Association.
   */
  @Override
  protected Association<CouchbasePersistentProperty> createAssociation() {
    return new Association<CouchbasePersistentProperty>(this, null);
  }

  /**
   * Returns the field name of the property.
   *
   * The field name can be different from the actual property name by using a
   * custom annotation.
   */
  @Override
  public String getFieldName() {
    org.springframework.data.couchbase.core.mapping.Field annotation = getField().
      getAnnotation(org.springframework.data.couchbase.core.mapping.Field.class);

    return annotation != null && StringUtils.hasText(annotation.value())
      ? annotation.value() : field.getName();
  }

}
