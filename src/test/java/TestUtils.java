//package indra.avitechcom;
//
//import indra.avitechcom.service.CommandService;
//import lombok.SneakyThrows;
//
//import java.lang.reflect.Field;
//
//public class TestUtils {
//
//    @SneakyThrows
//    public static void setTestField(Object classInstance, String fieldName, Object fieldValue) {
//        // Use reflection to access the private field 'name'
//        Field field = CommandService.class.getDeclaredField(fieldName);
//
//        // Make the private field accessible
//        field.setAccessible(true);
//
//        field.set(classInstance, fieldValue);
//    }
//}
