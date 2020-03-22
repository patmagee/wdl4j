package io.github.patmagee.wdl4j.v1;

import org.openwdl.wdl.v1.parser.WdlParser;
import org.openwdl.wdl.v1.parser.WdlParserBaseVisitor;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.*;
import io.github.patmagee.wdl4j.v1.typing.*;

import java.util.*;

public class WdlV1DocumentVisitor extends WdlParserBaseVisitor<WdlElement> {

    @Override
    public Type visitType_base(WdlParser.Type_baseContext ctx) {
        if (ctx.BOOLEAN() != null) {
            return BooleanType.getType();
        } else if (ctx.STRING() != null) {
            return StringType.getType();
        } else if (ctx.FILE() != null) {
            return FileType.getType();
        } else if (ctx.FLOAT() != null) {
            return FloatType.getType();
        } else if (ctx.INT() != null) {
            return IntType.getType();
        } else if (ctx.Identifier() != null) {
            return StructType.getType(ctx.Identifier().getText());
        } else if (ctx.OBJECT() != null) {
            return ObjectType.getType();
        } else {
            return (Type) super.visitType_base(ctx);
        }
    }

    @Override
    public MapType visitMap_type(WdlParser.Map_typeContext ctx) {
        Type left = visitWdl_type(ctx.wdl_type(0));
        Type right = visitWdl_type(ctx.wdl_type(1));
        return MapType.getType(left, right);
    }

    @Override
    public ArrayType visitArray_type(WdlParser.Array_typeContext ctx) {
        Type innerType = visitWdl_type(ctx.wdl_type());
        boolean nonEmpty = ctx.PLUS() != null && ctx.PLUS().getText().equals("+");
        return ArrayType.getType(innerType, nonEmpty);

    }

    @Override
    public PairType visitPair_type(WdlParser.Pair_typeContext ctx) {
        Type left = visitWdl_type(ctx.wdl_type(0));
        Type right = visitWdl_type(ctx.wdl_type(1));
        return PairType.getType(left, right);
    }

    @Override
    public Type visitWdl_type(WdlParser.Wdl_typeContext ctx) {
        Type type = (Type) super.visitWdl_type(ctx);
        if (ctx.OPTIONAL() != null) {
            return OptionalType.getType(type);
        }
        return type;
    }

    @Override
    public Declaration visitUnbound_decls(WdlParser.Unbound_declsContext ctx) {
        Type type = visitWdl_type(ctx.wdl_type());
        String name = ctx.Identifier().getText();
        return new Declaration(type, name);
    }

    @Override
    public Declaration visitBound_decls(WdlParser.Bound_declsContext ctx) {
        Type type = visitWdl_type(ctx.wdl_type());
        Expression expression = visitExpr(ctx.expr());
        String name = ctx.Identifier().getText();
        return new Declaration(type, name, expression);
    }

    @Override
    public Declaration visitAny_decls(WdlParser.Any_declsContext ctx) {
        return (Declaration) super.visitAny_decls(ctx);
    }

    @Override
    public Expression visitNumber(WdlParser.NumberContext ctx) {
        if (ctx.IntLiteral() != null) {
            return new IntLiteral(Integer.parseInt(ctx.IntLiteral().getText()));
        } else {
            return new FloatLiteral(Float.parseFloat(ctx.IntLiteral().getText()));
        }
    }

    @Override
    public Expression visitExpression_placeholder_option(WdlParser.Expression_placeholder_optionContext ctx) {
        Expression expression = null;
        if (ctx.number() != null) {
            expression = visitNumber(ctx.number());
        } else if (ctx.string() != null) {
            expression = visitString(ctx.string());
        }

        if (ctx.DEFAULT() != null) {
            return new DefaultPlaceholder(expression);
        } else if (ctx.SEP() != null) {
            return new SepPlaceholder(expression);
        } else {
            if (Boolean.parseBoolean(ctx.BoolLiteral().getText())) {
                return new TrueFalsePlaceholder(expression, TrueFalsePlaceholder.Condition.TRUE);
            } else {
                return new TrueFalsePlaceholder(expression, TrueFalsePlaceholder.Condition.FALSE);
            }
        }
    }

    @Override
    public StringLiteral.StringPart visitString_expr_part(WdlParser.String_expr_partContext ctx) {
        Expression expression = visitExpr(ctx.expr());
        List<Expression> placeholders = new ArrayList<>();
        if (ctx.expression_placeholder_option() != null) {
            for (WdlParser.Expression_placeholder_optionContext placeholder : ctx.expression_placeholder_option()) {
                placeholders.add(visitExpression_placeholder_option(placeholder));
            }
        }
        return new StringLiteral.StringPart(placeholders, expression);
    }

    @Override
    public StringLiteral visitString(WdlParser.StringContext ctx) {
        List<StringLiteral.StringPart> stringParts = new ArrayList<>();
        if (ctx.string_part() != null) {
            stringParts.add(new StringLiteral.StringPart(ctx.string_part().getText()));
        }
        if (ctx.string_expr_with_string_part() != null) {
            for (WdlParser.String_expr_with_string_partContext part : ctx.string_expr_with_string_part()) {
                stringParts.add(visitString_expr_part(part.string_expr_part()));
                if (part.string_part() != null) {
                    stringParts.add(new StringLiteral.StringPart(part.string_part().getText()));
                }
            }
        }
        return new StringLiteral(stringParts);
    }

    @Override
    public Expression visitPrimitive_literal(WdlParser.Primitive_literalContext ctx) {
        if (ctx.BoolLiteral() != null) {
            return new BooleanLiteral(Boolean.parseBoolean(ctx.BoolLiteral().getText()));
        } else if (ctx.number() != null) {
            return visitNumber(ctx.number());
        } else if (ctx.Identifier() != null) {
            return new VariableReference(ctx.Identifier().getText());
        } else {
            return visitString(ctx.string());
        }
    }

    @Override
    public Expression visitExpr(WdlParser.ExprContext ctx) {
        return (Expression) super.visitExpr(ctx);
    }

    @Override
    public LogicalOr visitLor(WdlParser.LorContext ctx) {
        return new LogicalOr((Expression) visitChildren(ctx.expr_infix0()),
                             (Expression) visitChildren(ctx.expr_infix1()));
    }

    @Override
    public LogicalAnd visitLand(WdlParser.LandContext ctx) {
        return new LogicalAnd((Expression) visitChildren(ctx.expr_infix1()),
                              (Expression) visitChildren(ctx.expr_infix2()));
    }

    @Override
    public EqualTo visitEqeq(WdlParser.EqeqContext ctx) {
        return new EqualTo((Expression) visitChildren(ctx.expr_infix2()),
                           (Expression) visitChildren(ctx.expr_infix3()));
    }

    @Override
    public LessThan visitLt(WdlParser.LtContext ctx) {
        return new LessThan((Expression) visitChildren(ctx.expr_infix2()),
                            (Expression) visitChildren(ctx.expr_infix3()));
    }

    @Override
    public GreaterThanOrEqual visitGte(WdlParser.GteContext ctx) {

        return new GreaterThanOrEqual((Expression) visitChildren(ctx.expr_infix2()),
                                      (Expression) visitChildren(ctx.expr_infix3()));
    }

    @Override
    public NotEqualTo visitNeq(WdlParser.NeqContext ctx) {
        return new NotEqualTo((Expression) visitChildren(ctx.expr_infix2()),
                              (Expression) visitChildren(ctx.expr_infix3()));
    }

    @Override
    public LessThanOrEqual visitLte(WdlParser.LteContext ctx) {

        return new LessThanOrEqual((Expression) visitChildren(ctx.expr_infix2()),
                                   (Expression) visitChildren(ctx.expr_infix3()));
    }

    @Override
    public GreaterThan visitGt(WdlParser.GtContext ctx) {
        return new GreaterThan((Expression) visitChildren(ctx.expr_infix2()),
                               (Expression) visitChildren(ctx.expr_infix3()));
    }

    @Override
    public Add visitAdd(WdlParser.AddContext ctx) {
        return new Add((Expression) visitChildren(ctx.expr_infix3()), (Expression) visitChildren(ctx.expr_infix4()));
    }

    @Override
    public Subtract visitSub(WdlParser.SubContext ctx) {
        return new Subtract((Expression) visitChildren(ctx.expr_infix3()),
                            (Expression) visitChildren(ctx.expr_infix4()));
    }

    @Override
    public Mod visitMod(WdlParser.ModContext ctx) {
        return new Mod((Expression) visitChildren(ctx.expr_infix4()), (Expression) visitChildren(ctx.expr_infix5()));
    }

    @Override
    public Multiply visitMul(WdlParser.MulContext ctx) {
        return new Multiply((Expression) visitChildren(ctx.expr_infix4()),
                            (Expression) visitChildren(ctx.expr_infix5()));
    }

    @Override
    public Divide visitDivide(WdlParser.DivideContext ctx) {
        return new Divide((Expression) visitChildren(ctx.expr_infix4()), (Expression) visitChildren(ctx.expr_infix5()));
    }

    @Override
    public PairLiteral visitPair_literal(WdlParser.Pair_literalContext ctx) {
        Expression left = visitExpr(ctx.expr(0));
        Expression right = visitExpr(ctx.expr(1));
        return new PairLiteral(left, right);
    }

    @Override
    public EngineFunction visitApply(WdlParser.ApplyContext ctx) {
        String name = ctx.Identifier().getText();
        List<Expression> arguments = new ArrayList<>();
        if (ctx.expr() != null) {
            for (WdlParser.ExprContext exprContext : ctx.expr()) {
                arguments.add(visitExpr(exprContext));
            }
        }
        return new EngineFunction(name, arguments);
    }

    @Override
    public Expression visitExpression_group(WdlParser.Expression_groupContext ctx) {
        return visitExpr(ctx.expr());
    }

    @Override
    public Expression visitPrimitives(WdlParser.PrimitivesContext ctx) {
        return visitPrimitive_literal(ctx.primitive_literal());
    }

    @Override
    public VariableReference visitLeft_name(WdlParser.Left_nameContext ctx) {
        return new VariableReference(ctx.Identifier().getText());
    }

    @Override
    public IndexedAccessor visitAt(WdlParser.AtContext ctx) {
        return new IndexedAccessor((Expression) visitChildren(ctx.expr_core()), visitExpr(ctx.expr()));
    }

    @Override
    public Negate visitNegate(WdlParser.NegateContext ctx) {
        return new Negate(visitExpr(ctx.expr()));
    }

    @Override
    public Signed visitUnirarysigned(WdlParser.UnirarysignedContext ctx) {
        if (ctx.PLUS() != null) {
            return new Signed(Signed.Operation.PLUS, visitExpr(ctx.expr()));
        } else {
            return new Signed(Signed.Operation.MINUS, visitExpr(ctx.expr()));
        }
    }

    @Override
    public MapLiteral visitMap_literal(WdlParser.Map_literalContext ctx) {

        List<MapLiteral.MapEntry> entries = new ArrayList<>();
        if (ctx.expr() != null) {
            int size = ctx.expr().size();
            for (int i = 0; i < size; i += 2) {
                Expression key = visitExpr(ctx.expr(i));
                Expression value = visitExpr(ctx.expr(i + 1));
                entries.add(new MapLiteral.MapEntry(key, value));
            }
        }

        return new MapLiteral(entries);
    }

    @Override
    public IfThenElse visitIfthenelse(WdlParser.IfthenelseContext ctx) {
        Expression condition = visitExpr(ctx.expr(0));
        Expression ifTrue = visitExpr(ctx.expr(1));
        Expression ifFalse = visitExpr(ctx.expr(2));
        return new IfThenElse(condition, ifTrue, ifFalse);

    }

    @Override
    public DotAccessor visitGet_name(WdlParser.Get_nameContext ctx) {
        return new DotAccessor((Expression) visitChildren(ctx.expr_core()), ctx.Identifier().getText());
    }

    @Override
    public ObjectLiteral visitObject_literal(WdlParser.Object_literalContext ctx) {
        Map<String, Expression> values = new HashMap<>();
        if (!ctx.isEmpty()) {
            for (int i = 0; i < ctx.Identifier().size(); i++) {
                values.put(ctx.Identifier(i).getText(), visitExpr(ctx.expr(i)));
            }
        }
        return new ObjectLiteral(values);
    }

    @Override
    public ArrayLiteral visitArray_literal(WdlParser.Array_literalContext ctx) {
        List<Expression> values = new ArrayList<>();
        if (!ctx.isEmpty()) {
            for (int i = 0; i < ctx.expr().size(); i++) {
                values.add(visitExpr(ctx.expr(i)));
            }
        }
        return new ArrayLiteral(values);
    }

    @Override
    public Version visitVersion(WdlParser.VersionContext ctx) {
        return new Version(ctx.RELEASE_VERSION().getText());
    }

    @Override
    public Import.ImportAlias visitImport_alias(WdlParser.Import_aliasContext ctx) {
        String name = ctx.Identifier(0).getText();
        String alias = ctx.Identifier(1).getText();
        return new Import.ImportAlias(name, alias);
    }

    @Override
    public Import visitImport_doc(WdlParser.Import_docContext ctx) {
        StringLiteral importUrl = visitString(ctx.string());
        String name = null;
        if (ctx.import_as() !=null){
            name = ctx.import_as().Identifier().getText();
        }
        List<Import.ImportAlias> aliases = new ArrayList<>();
        if (ctx.import_alias() != null) {
            for (int i = 0; i < ctx.import_alias().size(); i++) {
                aliases.add(visitImport_alias(ctx.import_alias(i)));
            }
        }
        return new Import(importUrl, name, aliases);
    }

    @Override
    public Struct visitStruct(WdlParser.StructContext ctx) {
        String name = ctx.Identifier().getText();
        List<Declaration> members = new ArrayList<>();
        if (ctx.unbound_decls() != null) {
            for (int i = 0; i < ctx.unbound_decls().size(); i++) {
                members.add(visitUnbound_decls(ctx.unbound_decls(i)));
            }
        }
        return new Struct(name, members);

    }

    @Override
    public ParameterMeta visitParameter_meta(WdlParser.Parameter_metaContext ctx) {
        return new ParameterMeta(visitKeyValueConetxt(ctx.meta_kv()));
    }

    @Override
    public Meta visitMeta(WdlParser.MetaContext ctx) {
        return new Meta(visitKeyValueConetxt(ctx.meta_kv()));
    }

    private Map<String, Expression> visitKeyValueConetxt(List<WdlParser.Meta_kvContext> ctx) {
        Map<String, Expression> attributes = new HashMap<>();
        if (ctx != null) {
            for (int i = 0; i < ctx.size(); i++) {
                WdlParser.Meta_kvContext kvContext = ctx.get(i);
                String key = kvContext.Identifier().getText();
                Expression value = visitExpr(kvContext.expr());
                attributes.put(key, value);
            }
        }
        return attributes;
    }

    @Override
    public Runtime visitTask_runtime(WdlParser.Task_runtimeContext ctx) {
        Map<String, Expression> attributes = new HashMap<>();
        if (ctx.task_runtime_kv() != null) {
            for (int i = 0; i < ctx.task_runtime_kv().size(); i++) {
                WdlParser.Task_runtime_kvContext kvContext = ctx.task_runtime_kv(i);
                String key = kvContext.Identifier().getText();
                Expression value = visitExpr(kvContext.expr());
                attributes.put(key, value);
            }
        }
        return new Runtime(attributes);
    }

    @Override
    public Inputs visitTask_input(WdlParser.Task_inputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.any_decls() != null) {
            for (int i = 0; i < ctx.any_decls().size(); i++) {
                declarations.add(visitAny_decls(ctx.any_decls(i)));
            }
        }
        return new Inputs(declarations);
    }

    @Override
    public Outputs visitTask_output(WdlParser.Task_outputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.bound_decls() != null) {
            for (int i = 0; i < ctx.bound_decls().size(); i++) {
                declarations.add(visitBound_decls(ctx.bound_decls(i)));
            }
        }
        return new Outputs(declarations);
    }

    @Override
    public Command.CommandPart visitTask_command_expr_part(WdlParser.Task_command_expr_partContext ctx) {
        Expression expression = visitExpr(ctx.expr());
        List<Expression> placeholders = new ArrayList<>();
        if (ctx.expression_placeholder_option() != null) {
            for (WdlParser.Expression_placeholder_optionContext placeholder : ctx.expression_placeholder_option()) {
                placeholders.add(visitExpression_placeholder_option(placeholder));
            }
        }
        return new Command.CommandPart(placeholders, expression);
    }

    @Override
    public Command visitTask_command(WdlParser.Task_commandContext ctx) {
        List<Command.CommandPart> commandParts = new ArrayList<>();
        if (ctx.task_command_string_part() != null) {
            commandParts.add(new Command.CommandPart(ctx.task_command_string_part().getText()));
        }
        if (ctx.task_command_expr_with_string() != null) {
            for (WdlParser.Task_command_expr_with_stringContext part : ctx.task_command_expr_with_string()) {
                commandParts.add(visitTask_command_expr_part(part.task_command_expr_part()));
                if (part.task_command_string_part() != null) {
                    commandParts.add(new Command.CommandPart(part.task_command_string_part().getText()));
                }
            }
        }
        return new Command(commandParts);
    }

    @Override
    public WdlElement visitTask_element(WdlParser.Task_elementContext ctx) {
        return super.visitTask_element(ctx);
    }

    @Override
    public Task visitTask(WdlParser.TaskContext ctx) {
        String name = ctx.Identifier().getText();
        List<Declaration> declarations = new ArrayList<>();
        Inputs inputs = null;
        Outputs outputs = null;
        Runtime runtime = null;
        ParameterMeta parameterMeta = null;
        Meta meta = null;
        Command command = null;

        List<WdlParser.Task_elementContext> elements = ctx.task_element();
        if (elements != null) {
            for (int i = 0; i < elements.size(); i++) {
                WdlParser.Task_elementContext element = elements.get(i);
                if (element.bound_decls() != null) {
                    declarations.add(visitBound_decls(element.bound_decls()));
                } else if (element.task_input() != null) {
                    inputs = visitTask_input(element.task_input());
                } else if (element.task_output() != null) {
                    outputs = visitTask_output(element.task_output());
                } else if (element.task_command() != null) {
                    command = visitTask_command(element.task_command());
                } else if (element.task_runtime() != null) {
                    runtime = visitTask_runtime(element.task_runtime());
                } else if (element.parameter_meta() != null) {
                    parameterMeta = visitParameter_meta(element.parameter_meta());
                } else if (element.meta() != null) {
                    meta = visitMeta(element.meta());
                }
            }
        }
        return new Task(name, inputs, declarations, command, runtime, outputs, meta, parameterMeta);
    }

    @Override
    public Call visitCall(WdlParser.CallContext ctx) {
        String name = ctx.Identifier().getText();
        String alias = ctx.call_alias() != null ? ctx.call_alias().Identifier().getText() : null;
        Map<String, Expression> inputs = new HashMap<>();
        if (ctx.call_body() != null && ctx.call_body().call_inputs() != null) {
            WdlParser.Call_inputsContext callInputs = ctx.call_body().call_inputs();
            if (callInputs.call_input() != null) {
                for (int i = 0; i < callInputs.call_input().size(); i++) {
                    WdlParser.Call_inputContext callInput = callInputs.call_input(i);
                    String inputName = callInput.Identifier().getText();
                    Expression inputValue = visitExpr(callInput.expr());
                    inputs.put(inputName, inputValue);
                }
            }
        }

        return new Call(name, alias, inputs);
    }

    @Override
    public Scatter visitScatter(WdlParser.ScatterContext ctx) {
        String varname = ctx.Identifier().getText();
        Expression expression = visitExpr(ctx.expr());
        List<Declaration> declarations = new ArrayList<>();
        List<WorkflowElement> workflowElements = new ArrayList<>();
        if (ctx.inner_workflow_element() != null) {
            for (int i = 0; i < ctx.inner_workflow_element().size(); i++) {
                WdlElement element = visitChildren(ctx.inner_workflow_element(i));
                if (element instanceof WorkflowElement) {
                    workflowElements.add((WorkflowElement) element);
                } else if (element instanceof Declaration) {
                    declarations.add((Declaration) element);
                }
            }
        }

        return new Scatter(varname, expression, declarations, workflowElements);
    }

    @Override
    public Conditional visitConditional(WdlParser.ConditionalContext ctx) {
        Expression condition = visitExpr(ctx.expr());
        List<Declaration> declarations = new ArrayList<>();
        List<WorkflowElement> workflowElements = new ArrayList<>();
        if (ctx.inner_workflow_element() != null) {
            for (int i = 0; i < ctx.inner_workflow_element().size(); i++) {
                WdlElement element = visitChildren(ctx.inner_workflow_element(i));
                if (element instanceof WorkflowElement) {
                    workflowElements.add((WorkflowElement) element);
                } else if (element instanceof Declaration) {
                    declarations.add((Declaration) element);
                }
            }
        }
        return new Conditional(condition, declarations, workflowElements);
    }

    @Override
    public Inputs visitWorkflow_input(WdlParser.Workflow_inputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.any_decls() != null) {
            for (int i = 0; i < ctx.any_decls().size(); i++) {
                declarations.add(visitAny_decls(ctx.any_decls(i)));
            }
        }
        return new Inputs(declarations);
    }

    @Override
    public Outputs visitWorkflow_output(WdlParser.Workflow_outputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.bound_decls() != null) {
            for (int i = 0; i < ctx.bound_decls().size(); i++) {
                declarations.add(visitBound_decls(ctx.bound_decls(i)));
            }
        }
        return new Outputs(declarations);
    }

    @Override
    public Inputs visitInput(WdlParser.InputContext ctx) {
        return visitWorkflow_input(ctx.workflow_input());
    }

    @Override
    public Outputs visitOutput(WdlParser.OutputContext ctx) {
        return visitWorkflow_output(ctx.workflow_output());
    }

    @Override
    public Meta visitMeta_element(WdlParser.Meta_elementContext ctx) {
        return visitMeta(ctx.meta());
    }

    @Override
    public ParameterMeta visitParameter_meta_element(WdlParser.Parameter_meta_elementContext ctx) {
        return visitParameter_meta(ctx.parameter_meta());
    }

    @Override
    public Workflow visitWorkflow(WdlParser.WorkflowContext ctx) {
        String name = ctx.Identifier().getText();
        List<WorkflowElement> elements = new ArrayList<>();
        List<Declaration> declarations = new ArrayList<>();
        Inputs inputs = null;
        Outputs outputs = null;
        ParameterMeta parameterMeta = null;
        Meta meta = null;

        for (WdlParser.Workflow_elementContext elementContext : ctx.workflow_element()) {
            WdlElement element = visitChildren(elementContext);
            if (element instanceof Inputs) {
                inputs = (Inputs) element;
            } else if (element instanceof Outputs) {
                outputs = (Outputs) element;
            } else if (element instanceof Declaration) {
                declarations.add((Declaration) element);
            } else if (element instanceof WorkflowElement) {
                elements.add((WorkflowElement) element);
            } else if (element instanceof Meta) {
                meta = (Meta) element;
            } else if (element instanceof ParameterMeta) {
                parameterMeta = (ParameterMeta) element;
            }
        }

        return new Workflow(name, inputs, declarations, elements, outputs, meta, parameterMeta);
    }

    @Override
    public WdlElement visitDocument_element(WdlParser.Document_elementContext ctx) {
        return super.visitDocument_element(ctx);
    }

    @Override
    public Document visitDocument(WdlParser.DocumentContext ctx) {
        Version version = visitVersion(ctx.version());
        List<Import> imports = new ArrayList<>();
        List<Struct> structs = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        Workflow workflow = ctx.workflow() != null ? visitWorkflow(ctx.workflow()) : null;
        for (WdlParser.Document_elementContext elementContext : ctx.document_element()) {
            WdlElement element = visitChildren(elementContext);
            if (element instanceof Import) {
                imports.add((Import) element);
            } else if (element instanceof Struct) {
                structs.add((Struct) element);
            } else if (element instanceof Task) {
                tasks.add((Task) element);
            }
        }

        return new Document(version, imports, structs, tasks, workflow);
    }
}
