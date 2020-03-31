package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.*;
import io.github.patmagee.wdl4j.v1.expression.literal.*;
import io.github.patmagee.wdl4j.v1.typing.*;
import org.openwdl.wdl.v1.parser.WdlParser;
import org.openwdl.wdl.v1.parser.WdlParserBaseVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public BinaryExpression visitLor(WdlParser.LorContext ctx) {
        return new BinaryExpression(visitExpr_infix0(ctx.expr_infix0()),
                                    visitExpr_infix1(ctx.expr_infix1()),
                                    BinaryExpression.BinaryOperation.LOGICAL_OR);
    }

    @Override
    public BinaryExpression visitLand(WdlParser.LandContext ctx) {
        return new BinaryExpression(visitExpr_infix1(ctx.expr_infix1()),
                                    visitExpr_infix2(ctx.expr_infix2()),
                                    BinaryExpression.BinaryOperation.LOGICAL_AND);
    }

    @Override
    public BinaryExpression visitEqeq(WdlParser.EqeqContext ctx) {
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.NOT_EQUAL_TO);
    }

    @Override
    public BinaryExpression visitLt(WdlParser.LtContext ctx) {
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.LESS_THAN);
    }

    @Override
    public BinaryExpression visitGte(WdlParser.GteContext ctx) {

        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.GREATER_THAN_OR_EQUAL);
    }

    @Override
    public BinaryExpression visitNeq(WdlParser.NeqContext ctx) {
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.NOT_EQUAL_TO);
    }

    @Override
    public BinaryExpression visitLte(WdlParser.LteContext ctx) {

        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.LESS_THAN_OR_EQUAL);
    }

    @Override
    public BinaryExpression visitGt(WdlParser.GtContext ctx) {
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.GREATER_THAN);
    }

    @Override
    public BinaryExpression visitAdd(WdlParser.AddContext ctx) {
        return new BinaryExpression(visitExpr_infix3(ctx.expr_infix3()),
                                    visitExpr_infix4(ctx.expr_infix4()),
                                    BinaryExpression.BinaryOperation.ADD);
    }

    @Override
    public BinaryExpression visitSub(WdlParser.SubContext ctx) {
        return new BinaryExpression(visitExpr_infix3(ctx.expr_infix3()),
                                    visitExpr_infix4(ctx.expr_infix4()),
                                    BinaryExpression.BinaryOperation.SUBTRACT);
    }

    @Override
    public BinaryExpression visitMod(WdlParser.ModContext ctx) {
        return new BinaryExpression(visitExpr_infix4(ctx.expr_infix4()),
                                    (Expression) visitExpr_infix5(ctx.expr_infix5()),
                                    BinaryExpression.BinaryOperation.MOD);
    }

    @Override
    public BinaryExpression visitMul(WdlParser.MulContext ctx) {
        return new BinaryExpression(visitExpr_infix4(ctx.expr_infix4()),
                                    (Expression) visitExpr_infix5(ctx.expr_infix5()),
                                    BinaryExpression.BinaryOperation.MULTIPLY);
    }

    @Override
    public BinaryExpression visitDivide(WdlParser.DivideContext ctx) {
        return new BinaryExpression(visitExpr_infix4(ctx.expr_infix4()),
                                    (Expression) visitExpr_infix5(ctx.expr_infix5()),
                                    BinaryExpression.BinaryOperation.DIVIDE);
    }

    private Expression visitExpr_infix0(WdlParser.Expr_infix0Context ctx) {
        if (ctx instanceof WdlParser.LorContext) {
            return visitLor((WdlParser.LorContext) ctx);
        } else if (ctx instanceof WdlParser.Infix1Context) {
            return (Expression) visitInfix1((WdlParser.Infix1Context) ctx);
        } else {
            return (Expression) visitChildren(ctx);
        }
    }

    private Expression visitExpr_infix1(WdlParser.Expr_infix1Context ctx) {
        if (ctx instanceof WdlParser.LandContext) {
            return visitLand((WdlParser.LandContext) ctx);
        } else if (ctx instanceof WdlParser.Infix2Context) {
            return (Expression) visitInfix2((WdlParser.Infix2Context) ctx);
        } else {
            return (Expression) visitChildren(ctx);
        }
    }

    private Expression visitExpr_infix2(WdlParser.Expr_infix2Context ctx) {
        if (ctx instanceof WdlParser.EqeqContext) {
            return visitEqeq((WdlParser.EqeqContext) ctx);
        } else if (ctx instanceof WdlParser.GteContext) {
            return visitGte((WdlParser.GteContext) ctx);
        } else if (ctx instanceof WdlParser.GtContext) {
            return visitGt((WdlParser.GtContext) ctx);
        } else if (ctx instanceof WdlParser.LtContext) {
            return visitLt((WdlParser.LtContext) ctx);
        } else if (ctx instanceof WdlParser.LteContext) {
            return visitLte((WdlParser.LteContext) ctx);
        } else if (ctx instanceof WdlParser.Infix3Context) {
            return (Expression) visitInfix3((WdlParser.Infix3Context) ctx);
        } else if (ctx instanceof WdlParser.NeqContext) {
            return visitNeq((WdlParser.NeqContext) ctx);
        } else {
            return (Expression) visitChildren(ctx);
        }
    }

    private Expression visitExpr_infix3(WdlParser.Expr_infix3Context ctx) {
        if (ctx instanceof WdlParser.AddContext) {
            return visitAdd((WdlParser.AddContext) ctx);
        } else if (ctx instanceof WdlParser.SubContext) {
            return visitSub((WdlParser.SubContext) ctx);
        } else if (ctx instanceof WdlParser.Infix4Context) {
            return (Expression) visitInfix4((WdlParser.Infix4Context) ctx);
        } else {
            return (Expression) visitChildren(ctx);
        }
    }

    private Expression visitExpr_infix4(WdlParser.Expr_infix4Context ctx) {
        if (ctx instanceof WdlParser.ModContext) {
            return visitMod((WdlParser.ModContext) ctx);
        } else if (ctx instanceof WdlParser.MulContext) {
            return visitMul((WdlParser.MulContext) ctx);
        } else if (ctx instanceof WdlParser.DivideContext) {
            return visitDivide((WdlParser.DivideContext) ctx);
        } else if (ctx instanceof WdlParser.Infix5Context) {
            return (Expression) visitInfix5((WdlParser.Infix5Context) ctx);
        } else {
            return (Expression) visitChildren(ctx);
        }
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
            return new Signed(visitExpr(ctx.expr()), Signed.Operation.PLUS);
        } else {
            return new Signed(visitExpr(ctx.expr()), Signed.Operation.MINUS);
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
        if (ctx.import_as() != null) {
            name = ctx.import_as().Identifier().getText();
        }
        List<Import.ImportAlias> aliases = new ArrayList<>();
        if (ctx.import_alias() != null) {
            for (int i = 0; i < ctx.import_alias().size(); i++) {
                aliases.add(visitImport_alias(ctx.import_alias(i)));
            }
        }
        return Import.newBuilder().withUrl(importUrl).withName(name).withAliases(aliases).build();
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
        return Struct.newBuilder().withName(name).withMembers(members).build();

    }

    @Override
    public ParameterMeta visitParameter_meta(WdlParser.Parameter_metaContext ctx) {
        return ParameterMeta.newBuilder().withAttributes(visitKeyValueConetxt(ctx.meta_kv())).build();
    }

    @Override
    public Meta visitMeta(WdlParser.MetaContext ctx) {
        return Meta.newBuilder().withAttributes(visitKeyValueConetxt(ctx.meta_kv())).build();
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
        return Runtime.newBuilder().withAttributes(attributes).build();
    }

    @Override
    public Inputs visitTask_input(WdlParser.Task_inputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.any_decls() != null) {
            for (int i = 0; i < ctx.any_decls().size(); i++) {
                declarations.add(visitAny_decls(ctx.any_decls(i)));
            }
        }
        return Inputs.newBuilder().withDeclarations(declarations).build();
    }

    @Override
    public Outputs visitTask_output(WdlParser.Task_outputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.bound_decls() != null) {
            for (int i = 0; i < ctx.bound_decls().size(); i++) {
                declarations.add(visitBound_decls(ctx.bound_decls(i)));
            }
        }
        return Outputs.newBuilder().withDeclarations(declarations).build();
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
        return Command.newBuilder().withCommandParts(commandParts).build();
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
        return Task.newBuilder()
                   .withName(name)
                   .withInputs(inputs)
                   .withDeclarations(declarations)
                   .withCommand(command)
                   .withRuntime(runtime)
                   .withOutputs(outputs)
                   .withMeta(meta)
                   .withParameterMeta(parameterMeta)
                   .build();
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
        return Call.newBuilder().withTaskName(name).withCallAlias(alias).withInputs(inputs).build();
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

        return Scatter.newBuilder()
                      .withVarname(varname)
                      .withExpression(expression)
                      .withDeclarations(declarations)
                      .withWorkflowElements(workflowElements)
                      .build();
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
        return Conditional.newBuilder()
                          .withExpression(condition)
                          .withDeclarations(declarations)
                          .withElements(workflowElements)
                          .build();
    }

    @Override
    public Inputs visitWorkflow_input(WdlParser.Workflow_inputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.any_decls() != null) {
            for (int i = 0; i < ctx.any_decls().size(); i++) {
                declarations.add(visitAny_decls(ctx.any_decls(i)));
            }
        }
        return Inputs.newBuilder().withDeclarations(declarations).build();
    }

    @Override
    public Outputs visitWorkflow_output(WdlParser.Workflow_outputContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.bound_decls() != null) {
            for (int i = 0; i < ctx.bound_decls().size(); i++) {
                declarations.add(visitBound_decls(ctx.bound_decls(i)));
            }
        }
        return Outputs.newBuilder().withDeclarations(declarations).build();
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

        return Workflow.newBuilder()
                       .withName(name)
                       .withInputs(inputs)
                       .withDeclarations(declarations)
                       .withElements(elements)
                       .withOutputs(outputs)
                       .withMeta(meta)
                       .withParameterMeta(parameterMeta)
                       .build();
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

        return Document.newBuilder()
                       .withVersion(version)
                       .withImports(imports)
                       .withStructs(structs)
                       .withTasks(tasks)
                       .withWorkflow(workflow)
                       .build();
    }
}
