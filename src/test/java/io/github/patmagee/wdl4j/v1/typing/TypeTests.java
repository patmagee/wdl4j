package io.github.patmagee.wdl4j.v1.typing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TypeTests {

    @Test
    public void testPrimitiveTypesReturnInstanceName() {
        Assertions.assertSame(BooleanType.getType(), BooleanType.getType());
        Assertions.assertEquals("Boolean", BooleanType.getType().getTypeName());

        Assertions.assertSame(IntType.getType(), IntType.getType());
        Assertions.assertEquals("Int", IntType.getType().getTypeName());

        Assertions.assertSame(FloatType.getType(), FloatType.getType());
        Assertions.assertEquals("Float", FloatType.getType().getTypeName());

        Assertions.assertSame(FileType.getType(), FileType.getType());
        Assertions.assertEquals("File", FileType.getType().getTypeName());

        Assertions.assertSame(StringType.getType(), StringType.getType());
        Assertions.assertEquals("String", StringType.getType().getTypeName());

        Assertions.assertSame(ObjectType.getType(), ObjectType.getType());
        Assertions.assertEquals("Object", ObjectType.getType().getTypeName());
    }

    @Test
    public void testOptionalTypeWrapsInnerType() {
        OptionalType optionalType = OptionalType.getType(StringType.getType());
        Assertions.assertNotEquals(optionalType, OptionalType.getType(IntType.getType()));
        Assertions.assertEquals("String?", optionalType.getTypeName());
        Assertions.assertSame(optionalType.getInnerType(), StringType.getType());
        Assertions.assertEquals("String", optionalType.getInnerType().getTypeName());

    }

    @Test
    public void testArrayType() {
        ArrayType arrayType = ArrayType.getType(StringType.getType(), false);
        ArrayType arrayTypeNonEmpty = ArrayType.getType(StringType.getType(), true);
        Assertions.assertEquals(arrayType, ArrayType.getType(StringType.getType(), false));
        Assertions.assertNotEquals(arrayType, arrayTypeNonEmpty);
        Assertions.assertEquals("Array[String]", arrayType.getTypeName());
        Assertions.assertEquals("Array[String]+", arrayTypeNonEmpty.getTypeName());
        Assertions.assertTrue(arrayTypeNonEmpty.getNonEmpty());
        Assertions.assertFalse(arrayType.getNonEmpty());
    }

    @Test
    public void testStructType() {
        String name = "FirstStruct";
        String otherName = "SecondStruct";
        StructType structType = StructType.getType(name);
        StructType structTypeWithOtherName = StructType.getType(otherName);

        Assertions.assertEquals(structType, StructType.getType(name));
        Assertions.assertNotEquals(structType, structTypeWithOtherName);
        Assertions.assertEquals(name, structType.getTypeName());
    }

    @Test
    public void testMapType() {
        MapType mapType = MapType.getType(StringType.getType(), StringType.getType());
        MapType maptypeWithDifferentKeyType = MapType.getType(IntType.getType(), StringType.getType());
        MapType maptypeWithDifferentValueType = MapType.getType(StringType.getType(), IntType.getType());
        MapType maptypeWithDifferentKeyAndValueType = MapType.getType(IntType.getType(), IntType.getType());

        Assertions.assertNotEquals(mapType, maptypeWithDifferentKeyType);
        Assertions.assertNotEquals(mapType, maptypeWithDifferentValueType);
        Assertions.assertNotEquals(mapType, maptypeWithDifferentKeyAndValueType);
        Assertions.assertNotEquals(maptypeWithDifferentKeyType, maptypeWithDifferentValueType);
        Assertions.assertNotEquals(maptypeWithDifferentKeyType, maptypeWithDifferentKeyAndValueType);
        Assertions.assertNotEquals(maptypeWithDifferentValueType, maptypeWithDifferentKeyAndValueType);
        Assertions.assertEquals("Map[String,String]", mapType.getTypeName());
    }

    @Test
    public void testPairType() {
        PairType pairType = PairType.getType(StringType.getType(), StringType.getType());
        PairType pairTypeWithDifferentRight = PairType.getType(IntType.getType(), StringType.getType());
        PairType pairTypeWithDifferentLeft = PairType.getType(StringType.getType(), IntType.getType());
        PairType pairTypeWithDifferentLeftAndRight = PairType.getType(IntType.getType(), IntType.getType());

        Assertions.assertNotEquals(pairType, pairTypeWithDifferentRight);
        Assertions.assertNotEquals(pairType, pairTypeWithDifferentLeft);
        Assertions.assertNotEquals(pairType, pairTypeWithDifferentLeftAndRight);
        Assertions.assertNotEquals(pairTypeWithDifferentRight, pairTypeWithDifferentLeft);
        Assertions.assertNotEquals(pairTypeWithDifferentRight, pairTypeWithDifferentLeftAndRight);
        Assertions.assertNotEquals(pairTypeWithDifferentLeft, pairTypeWithDifferentLeftAndRight);
        Assertions.assertEquals("Pair[String,String]", pairType.getTypeName());
    }

    @Test
    public void testNestedTypes() {
        ArrayType nestedArray = ArrayType.getType(MapType.getType(StringType.getType(), IntType.getType()), false);
        MapType nestedMapType = MapType.getType(StringType.getType(),
                                                ArrayType.getType(PairType.getType(StringType.getType(),
                                                                                   IntType.getType()), true));
        Assertions.assertEquals("Array[Map[String,Int]]", nestedArray.getTypeName());
        Assertions.assertEquals("Map[String,Array[Pair[String,Int]]+]", nestedMapType.getTypeName());
        Assertions.assertEquals("Map[String,Array[Pair[String,Int]]+]?",
                                OptionalType.getType(nestedMapType).getTypeName());
    }

    @Test
    public void testGetStructTypeWithNullNameThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> StructType.getType(null));
    }

    @Test
    public void testGetArrayTypeWithNullInnerTypeThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> ArrayType.getType(null, false));
    }

    @Test
    public void testGetOptionalTypeWithNullInnerTypeThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> OptionalType.getType(null));
    }

    @Test
    public void testGetMapTypeWithNullValuesThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> MapType.getType(null, StringType.getType()));
        Assertions.assertThrows(NullPointerException.class, () -> MapType.getType(StringType.getType(), null));
        Assertions.assertThrows(NullPointerException.class, () -> MapType.getType(null, null));
    }

    @Test
    public void testGetPairTypeWithNullValuesThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> PairType.getType(null, StringType.getType()));
        Assertions.assertThrows(NullPointerException.class, () -> PairType.getType(StringType.getType(), null));
        Assertions.assertThrows(NullPointerException.class, () -> PairType.getType(null, null));
    }

    @Test
    public void testStringTypeCoercions() {
        StringType type = StringType.getType();
        Assertions.assertTrue(type.isCoercibleTo(type));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(type)));
        Assertions.assertTrue(type.isCoercibleTo(FileType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(FileType.getType())));

        Assertions.assertTrue(type.isCoercibleTo(IntType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(IntType.getType())));

        Assertions.assertTrue(type.isCoercibleTo(FloatType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(FloatType.getType())));

        Assertions.assertFalse(type.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(BooleanType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(ArrayType.getType(AnyType.getType(), false)));
        Assertions.assertFalse(type.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(type.isCoercibleTo(ObjectType.getType()));

    }

    @Test
    public void testIntTypeCoercions() {
        IntType type = IntType.getType();
        Assertions.assertTrue(type.isCoercibleTo(type));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(type)));
        Assertions.assertTrue(type.isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(StringType.getType())));
        Assertions.assertTrue(type.isCoercibleTo(FloatType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(FloatType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(BooleanType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(FileType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(ArrayType.getType(AnyType.getType(), false)));
        Assertions.assertFalse(type.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(type.isCoercibleTo(ObjectType.getType()));
    }

    @Test
    public void testFloatTypeCoercions() {
        FloatType type = FloatType.getType();
        Assertions.assertTrue(type.isCoercibleTo(type));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(type)));
        Assertions.assertTrue(type.isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(StringType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(IntType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(BooleanType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(FileType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(ArrayType.getType(AnyType.getType(), false)));
        Assertions.assertFalse(type.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(type.isCoercibleTo(ObjectType.getType()));
    }

    @Test
    public void testBooleanTypeCoercions() {
        BooleanType type = BooleanType.getType();
        Assertions.assertTrue(type.isCoercibleTo(type));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(type)));
        Assertions.assertTrue(type.isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(StringType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(IntType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(FloatType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(FloatType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(FileType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(ArrayType.getType(AnyType.getType(), false)));
        Assertions.assertFalse(type.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(type.isCoercibleTo(ObjectType.getType()));
    }

    @Test
    public void testFileTypeCoercions() {
        FileType type = FileType.getType();
        Assertions.assertTrue(type.isCoercibleTo(type));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(type)));
        Assertions.assertTrue(type.isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(type.isCoercibleTo(OptionalType.getType(StringType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(IntType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(FloatType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(FloatType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(type.isCoercibleTo(OptionalType.getType(BooleanType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(type.isCoercibleTo(ArrayType.getType(AnyType.getType(), false)));
        Assertions.assertFalse(type.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(type.isCoercibleTo(ObjectType.getType()));
    }

    @Test
    public void testOptionalTypeCannotCoerceToNonOptional() {
        Assertions.assertFalse(OptionalType.getType(AnyType.getType()).isCoercibleTo(AnyType.getType()));
    }

    @Test
    public void testArrayTypeCoercions() {
        ArrayType baseType = ArrayType.getType(AnyType.getType());
        Assertions.assertTrue(baseType.isCoercibleTo(baseType));
        Assertions.assertTrue(baseType.isCoercibleTo(OptionalType.getType(baseType)));
        //Cannot convert empty to non empty
        Assertions.assertFalse(baseType.isCoercibleTo(ArrayType.getType(AnyType.getType(), true)));
        //Can convert non empty to emtpy
        Assertions.assertTrue(ArrayType.getType(AnyType.getType(), true).isCoercibleTo(baseType));
        //Inner type conversions
        Assertions.assertTrue(ArrayType.getType(StringType.getType())
                                       .isCoercibleTo(ArrayType.getType(FileType.getType())));
        Assertions.assertTrue(ArrayType.getType(StringType.getType())
                                       .isCoercibleTo(ArrayType.getType(IntType.getType())));
        Assertions.assertFalse(ArrayType.getType(FloatType.getType())
                                        .isCoercibleTo(ArrayType.getType(FileType.getType())));

        //Special String Coercioon
        Assertions.assertTrue(ArrayType.getType(StringType.getType()).isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(ArrayType.getType(IntType.getType()).isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(ArrayType.getType(FloatType.getType()).isCoercibleTo(StringType.getType()));
        Assertions.assertTrue(ArrayType.getType(FileType.getType()).isCoercibleTo(StringType.getType()));

        Assertions.assertFalse(baseType.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(baseType.isCoercibleTo(ObjectType.getType()));

    }

    @Test
    public void testMapTypeCoercions() {
        MapType baseType = MapType.getType(AnyType.getType(), AnyType.getType());
        Assertions.assertTrue(baseType.isCoercibleTo(baseType));
        Assertions.assertTrue(baseType.isCoercibleTo(OptionalType.getType(baseType)));

        Map<String, Type> members = new HashMap<>();

        members.put("valid1", FileType.getType());

        StructType structType = StructType.getType("test", members);
        //Generic map with no names cannot map into struct
        Assertions.assertFalse(baseType.isCoercibleTo(structType));
        //Map with all the keys can map into struct
        Assertions.assertTrue(MapType.getType(StringType.getType(), FileType.getType(), Arrays.asList("valid1"))
                                     .isCoercibleTo(structType));
        members.put("valid2", IntType.getType());
        //map with some keys but no all cannnot map into struct
        Assertions.assertFalse(MapType.getType(StringType.getType(), FileType.getType(), Arrays.asList("valid1"))
                                      .isCoercibleTo(structType));
        //map with all the keys but non coercible values canot be mapped into struct
        Assertions.assertFalse(MapType.getType(StringType.getType(),
                                               FileType.getType(),
                                               Arrays.asList("valid1", "valid2")).isCoercibleTo(structType));

        //map with all they keys and all coercible types can be mapped into struct
        Assertions.assertTrue(MapType.getType(StringType.getType(),
                                              StringType.getType(),
                                              Arrays.asList("valid1", "valid2")).isCoercibleTo(structType));

        members.put("optional", OptionalType.getType(StringType.getType()));
        Assertions.assertTrue(MapType.getType(StringType.getType(),
                                              StringType.getType(),
                                              Arrays.asList("valid1", "valid2")).isCoercibleTo(structType));

        Assertions.assertFalse(baseType.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(StringType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FloatType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(ArrayType.getType(AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(ObjectType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
    }

    @Test
    public void testObjectTypeCoercion() {
        ObjectType baseType = ObjectType.getType();
        Assertions.assertTrue(baseType.isCoercibleTo(baseType));
        Assertions.assertTrue(baseType.isCoercibleTo(OptionalType.getType(baseType)));

        Map<String, Type> structMembers = new HashMap<>();
        Map<String, Type> objectMembers = new HashMap<>();

        structMembers.put("valid1", FileType.getType());

        StructType structType = StructType.getType("test", structMembers);
        //Generic object with no names cannot map into struct
        Assertions.assertFalse(baseType.isCoercibleTo(structType));

        objectMembers.put("valid1", StringType.getType());
        //object with all the keys can map into struct
        Assertions.assertTrue(ObjectType.getType(objectMembers).isCoercibleTo(structType));
        structMembers.put("valid2", IntType.getType());
        //object with some keys but no all cannnot map into struct
        Assertions.assertFalse(ObjectType.getType(objectMembers).isCoercibleTo(structType));
        objectMembers.put("valid2", FileType.getType());
        //map with all the keys but non coercible values canot be mapped into struct
        Assertions.assertFalse(ObjectType.getType(objectMembers).isCoercibleTo(structType));
        objectMembers.put("valid2", StringType.getType());
        Assertions.assertTrue(ObjectType.getType(objectMembers).isCoercibleTo(structType));
        structMembers.put("optional", OptionalType.getType(StringType.getType()));
        Assertions.assertTrue(ObjectType.getType(objectMembers).isCoercibleTo(structType));

        Assertions.assertFalse(baseType.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(StringType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FloatType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(ArrayType.getType(AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
    }

    @Test
    public void testStructTypeCoercions() {
        StructType baseType = StructType.getType("test");
        Assertions.assertTrue(baseType.isCoercibleTo(baseType));
        Assertions.assertTrue(baseType.isCoercibleTo(OptionalType.getType(baseType)));

        Map<String, Type> structMembers = new HashMap<>();
        structMembers.put("valid1", FileType.getType());
        Assertions.assertFalse(baseType.isCoercibleTo(StructType.getType("test2")));
        Assertions.assertFalse(baseType.isCoercibleTo(StructType.getType("test2", structMembers)));
        Assertions.assertTrue(baseType.isCoercibleTo(StructType.getType("test", structMembers)));

        Assertions.assertTrue(StructType.getType("test", structMembers)
                                        .isCoercibleTo(StructType.getType("test", structMembers)));
        Assertions.assertTrue(StructType.getType("test2", structMembers)
                                        .isCoercibleTo(StructType.getType("test", structMembers)));

        Assertions.assertFalse(StructType.getType("test2", Collections.singletonMap("valid1", IntType.getType()))
                                         .isCoercibleTo(StructType.getType("test", structMembers)));
        Assertions.assertFalse(StructType.getType("test2", Collections.singletonMap("valid2", FileType.getType()))
                                         .isCoercibleTo(StructType.getType("test", structMembers)));

        Assertions.assertFalse(baseType.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(StringType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FloatType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(ArrayType.getType(AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(PairType.getType(AnyType.getType(), AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(ObjectType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
    }

    @Test
    public void testPairTypeCoercion() {
        PairType baseType = PairType.getType(AnyType.getType(), AnyType.getType());
        Assertions.assertTrue(baseType.isCoercibleTo(baseType));
        Assertions.assertTrue(baseType.isCoercibleTo(OptionalType.getType(baseType)));

        Assertions.assertFalse(baseType.isCoercibleTo(BooleanType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(StringType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FileType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(IntType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(FloatType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(ArrayType.getType(AnyType.getType())));
        Assertions.assertFalse(baseType.isCoercibleTo(StructType.getType("test")));
        Assertions.assertFalse(baseType.isCoercibleTo(ObjectType.getType()));
        Assertions.assertFalse(baseType.isCoercibleTo(MapType.getType(AnyType.getType(), AnyType.getType())));
    }
}
