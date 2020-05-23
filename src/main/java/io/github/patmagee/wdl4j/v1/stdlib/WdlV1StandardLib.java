package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.api.StandardLib;
import io.github.patmagee.wdl4j.v1.exception.NoSuchReferenceException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WdlV1StandardLib implements StandardLib {

    private static final Map<String, EngineFunction> functionMap = registerFunctions();

    private static Map<String, EngineFunction> registerFunctions() {
        Map<String, EngineFunction> map = new HashMap<>();
        map.put("basename",new Basename());
        map.put("cross",new Cross());
        map.put("defined",new Defined());
        map.put("length",new Length());
        map.put("glob",new Glob());
        map.put("prefix",new Prefix());
        map.put("range",new Range());
        map.put("read_boolean",new ReadBoolean());
        map.put("read_int",new ReadInt());
        map.put("read_json",new ReadJson());
        map.put("read_lines",new ReadLines());
        map.put("read_map",new ReadMap());
        map.put("read_object",new ReadObject(false));
        map.put("read_objects",new ReadObject(true));
        map.put("read_string",new ReadString());
        map.put("read_tsv",new ReadTsv());
        map.put("round",new Rounding("round", Rounding.Direction.BOTH));
        map.put("ceil",new Rounding("ceil", Rounding.Direction.UP));
        map.put("floor", new Rounding("floor",Rounding.Direction.DOWN));
        map.put("select_all",new SelectAll());
        map.put("select_first",new SelectFirst());
        map.put("size",new Size());
        map.put("stdout",new Stdout());
        map.put("stderr",new Stderr());
        map.put("sub",new Sub());
        map.put("transpose",new Transpose());
        map.put("write_json",new WriteJson());
        map.put("write_lines",new WriteLines());
        map.put("write_map",new WriteMap());
        map.put("write_object",new WriteObject(false));
        map.put("write_objects",new WriteObject(true));
        map.put("write_tsv",new WriteTsv());
        map.put("zip",new Zip());
        return map;
    }

    @Override
    public Type evaluateReturnType(String name, List<Type> arguments) throws WdlValidationError {
        EngineFunction function = getFunction(name);
        function.checkArity(arguments);
        return function.evaluateReturnType(arguments);
    }

    @Override
    public EngineFunction getFunction(String name) throws NoSuchReferenceException {
        EngineFunction function = functionMap.get(name);
        if (function == null) {
            throw new NoSuchReferenceException("Engine function " + name + " does not exist");
        }
        return function;
    }
}
