package io.github.patmagee.wdl4j.v1.typing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTests {

    @Test
    public void testPrimitiveTypesReturnInstanceAndPringName() {
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
        Assertions.assertSame(optionalType, OptionalType.getType(StringType.getType()));
        Assertions.assertNotSame(optionalType, OptionalType.getType(IntType.getType()));
        Assertions.assertNotEquals(optionalType, OptionalType.getType(IntType.getType()));
        Assertions.assertEquals("String?", optionalType.getTypeName());
        Assertions.assertSame(optionalType.getInnerType(), StringType.getType());
        Assertions.assertEquals("String", optionalType.getInnerType().getTypeName());

    }

    @Test
    public void testArrayType() {
        ArrayType arrayType = ArrayType.getType(StringType.getType(), false);
        ArrayType arrayTypeNonEmpty = ArrayType.getType(StringType.getType(), true);

        Assertions.assertSame(arrayType, ArrayType.getType(StringType.getType(), false));
        Assertions.assertEquals(arrayType, ArrayType.getType(StringType.getType(), false));
        Assertions.assertNotEquals(arrayType, arrayTypeNonEmpty);
        Assertions.assertNotSame(arrayType, arrayTypeNonEmpty);
        Assertions.assertEquals("Array[String]", arrayType.getTypeName());
        Assertions.assertEquals("Array[String]+", arrayTypeNonEmpty.getTypeName());
        Assertions.assertTrue(arrayTypeNonEmpty.getNonEmpty());
        Assertions.assertFalse(arrayType.getNonEmpty());
        Assertions.assertSame(arrayType.getInnerType(), StringType.getType());
    }

    @Test
    public void testStructType() {
        String name = "FirstStruct";
        String otherName = "SecondStruct";
        StructType structType = StructType.getType(name);
        StructType structTypeWithOtherName = StructType.getType(otherName);

        Assertions.assertSame(structType, StructType.getType(name));
        Assertions.assertNotSame(structType, structTypeWithOtherName);
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

        Assertions.assertSame(mapType, MapType.getType(StringType.getType(), StringType.getType()));
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

        Assertions.assertSame(pairType, PairType.getType(StringType.getType(), StringType.getType()));
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
        Assertions.assertSame(nestedArray,
                              ArrayType.getType(MapType.getType(StringType.getType(), IntType.getType()), false));
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

}
