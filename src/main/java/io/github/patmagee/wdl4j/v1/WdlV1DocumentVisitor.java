package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.*;
import io.github.patmagee.wdl4j.v1.expression.literal.*;
import io.github.patmagee.wdl4j.v1.stdlib.WdlV1StandardLib;
import io.github.patmagee.wdl4j.v1.typing.*;
import org.openwdl.wdl.v1.parser.WdlParser;
import org.openwdl.wdl.v1.parser.WdlParserBaseVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WdlV1DocumentVisitor extends WdlParserBaseVisitor<WdlElement> {

    private AtomicInteger idCounter = new AtomicInteger(0);

    private int getNextId() {
        return idCounter.addAndGet(1);
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
        int id = getNextId();
        Type type = visitWdl_type(ctx.wdl_type());
        String name = ctx.Identifier().getText();
        return new Declaration(type, name, null, id);
    }

    @Override
    public Declaration visitBound_decls(WdlParser.Bound_declsContext ctx) {
        int id = getNextId();
        Type type = visitWdl_type(ctx.wdl_type());
        Expression expression = visitExpr(ctx.expr());
        String name = ctx.Identifier().getText();
        return new Declaration(type, name, expression, id);
    }

    @Override
    public Declaration visitAny_decls(WdlParser.Any_declsContext ctx) {
        return (Declaration) super.visitAny_decls(ctx);
    }

    @Override
    public Expression visitNumber(WdlParser.NumberContext ctx) {
        int id = getNextId();
        if (ctx.IntLiteral() != null) {
            return new IntLiteral(Integer.parseInt(ctx.IntLiteral().getText()), id);
        } else {
            return new FloatLiteral(Float.parseFloat(ctx.IntLiteral().getText()), id);
        }
    }

    @Override
    public Expression visitExpression_placeholder_option(WdlParser.Expression_placeholder_optionContext ctx) {
        int id = getNextId();
        Expression expression = null;

        if (ctx.number() != null) {
            expression = visitNumber(ctx.number());
        } else if (ctx.string() != null) {
            expression = visitString(ctx.string());
        }

        if (ctx.DEFAULT() != null) {
            return new DefaultPlaceholder(expression, id);
        } else if (ctx.SEP() != null) {
            return new SepPlaceholder(expression, id);
        } else {
            if (Boolean.parseBoolean(ctx.BoolLiteral().getText())) {
                return new TrueFalsePlaceholder(expression, TrueFalsePlaceholder.Condition.TRUE, id);
            } else {
                return new TrueFalsePlaceholder(expression, TrueFalsePlaceholder.Condition.FALSE, id);
            }
        }
    }

    @Override
    public StringLiteral.StringPart visitString_expr_part(WdlParser.String_expr_partContext ctx) {
        int id = getNextId();
        Expression expression = visitExpr(ctx.expr());
        List<Expression> placeholders = new ArrayList<>();
        if (ctx.expression_placeholder_option() != null) {
            for (WdlParser.Expression_placeholder_optionContext placeholder : ctx.expression_placeholder_option()) {
                placeholders.add(visitExpression_placeholder_option(placeholder));
            }
        }
        return new StringLiteral.StringPart(placeholders, expression, id);
    }

    @Override
    public StringLiteral visitString(WdlParser.StringContext ctx) {
        int id = getNextId();
        List<StringLiteral.StringPart> stringParts = new ArrayList<>();
        if (ctx.string_part() != null) {
            stringParts.add(new StringLiteral.StringPart(ctx.string_part().getText(), getNextId()));
        }
        if (ctx.string_expr_with_string_part() != null) {
            for (WdlParser.String_expr_with_string_partContext part : ctx.string_expr_with_string_part()) {
                stringParts.add(visitString_expr_part(part.string_expr_part()));
                if (part.string_part() != null) {
                    stringParts.add(new StringLiteral.StringPart(part.string_part().getText(), getNextId()));
                }
            }
        }
        return new StringLiteral(stringParts, id);
    }

    @Override
    public Expression visitPrimitive_literal(WdlParser.Primitive_literalContext ctx) {
        if (ctx.BoolLiteral() != null) {
            return new BooleanLiteral(Boolean.parseBoolean(ctx.BoolLiteral().getText()), getNextId());
        } else if (ctx.number() != null) {
            return visitNumber(ctx.number());
        } else if (ctx.Identifier() != null) {
            return new VariableReference(ctx.Identifier().getText(), getNextId());
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
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix0(ctx.expr_infix0()),
                                    visitExpr_infix1(ctx.expr_infix1()),
                                    BinaryExpression.BinaryOperation.LOGICAL_OR,
                                    id);
    }

    @Override
    public BinaryExpression visitLand(WdlParser.LandContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix1(ctx.expr_infix1()),
                                    visitExpr_infix2(ctx.expr_infix2()),
                                    BinaryExpression.BinaryOperation.LOGICAL_AND,
                                    id);
    }

    @Override
    public BinaryExpression visitEqeq(WdlParser.EqeqContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.EQUAL_TO,
                                    id);
    }

    @Override
    public BinaryExpression visitLt(WdlParser.LtContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.LESS_THAN,
                                    id);
    }

    @Override
    public BinaryExpression visitGte(WdlParser.GteContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.GREATER_THAN_OR_EQUAL,
                                    id);
    }

    @Override
    public BinaryExpression visitNeq(WdlParser.NeqContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.NOT_EQUAL_TO,
                                    id);
    }

    @Override
    public BinaryExpression visitLte(WdlParser.LteContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.LESS_THAN_OR_EQUAL,
                                    id);
    }

    @Override
    public BinaryExpression visitGt(WdlParser.GtContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix2(ctx.expr_infix2()),
                                    visitExpr_infix3(ctx.expr_infix3()),
                                    BinaryExpression.BinaryOperation.GREATER_THAN,
                                    id);
    }

    @Override
    public BinaryExpression visitAdd(WdlParser.AddContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix3(ctx.expr_infix3()),
                                    visitExpr_infix4(ctx.expr_infix4()),
                                    BinaryExpression.BinaryOperation.ADD,
                                    id);
    }

    @Override
    public BinaryExpression visitSub(WdlParser.SubContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix3(ctx.expr_infix3()),
                                    visitExpr_infix4(ctx.expr_infix4()),
                                    BinaryExpression.BinaryOperation.SUBTRACT,
                                    id);
    }

    @Override
    public BinaryExpression visitMod(WdlParser.ModContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix4(ctx.expr_infix4()),
                                    (Expression) visitExpr_infix5(ctx.expr_infix5()),
                                    BinaryExpression.BinaryOperation.MOD,
                                    id);
    }

    @Override
    public BinaryExpression visitMul(WdlParser.MulContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix4(ctx.expr_infix4()),
                                    (Expression) visitExpr_infix5(ctx.expr_infix5()),
                                    BinaryExpression.BinaryOperation.MULTIPLY,
                                    id);
    }

    @Override
    public BinaryExpression visitDivide(WdlParser.DivideContext ctx) {
        int id = getNextId();
        return new BinaryExpression(visitExpr_infix4(ctx.expr_infix4()),
                                    (Expression) visitExpr_infix5(ctx.expr_infix5()),
                                    BinaryExpression.BinaryOperation.DIVIDE,
                                    id);
    }

    @Override
    public PairLiteral visitPair_literal(WdlParser.Pair_literalContext ctx) {
        int id = getNextId();
        Expression left = visitExpr(ctx.expr(0));
        Expression right = visitExpr(ctx.expr(1));
        return new PairLiteral(left, right, id);
    }

    @Override
    public ApplyFunction visitApply(WdlParser.ApplyContext ctx) {
        int id = getNextId();
        String name = ctx.Identifier().getText();
        List<Expression> arguments = new ArrayList<>();
        if (ctx.expr() != null) {
            for (WdlParser.ExprContext exprContext : ctx.expr()) {
                arguments.add(visitExpr(exprContext));
            }
        }
        return new ApplyFunction(name, arguments, id);
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
        int id = getNextId();
        return new VariableReference(ctx.Identifier().getText(), id);
    }

    @Override
    public IndexedAccessor visitAt(WdlParser.AtContext ctx) {
        int id = getNextId();
        return new IndexedAccessor((Expression) visitChildren(ctx.expr_core()), visitExpr(ctx.expr()), id);
    }

    @Override
    public Negate visitNegate(WdlParser.NegateContext ctx) {

        int id = getNextId();
        return new Negate(visitExpr(ctx.expr()), id);
    }

    @Override
    public Signed visitUnirarysigned(WdlParser.UnirarysignedContext ctx) {
        int id = getNextId();
        if (ctx.PLUS() != null) {
            return new Signed(visitExpr(ctx.expr()), Signed.Operation.PLUS, id);
        } else {
            return new Signed(visitExpr(ctx.expr()), Signed.Operation.MINUS, id);
        }
    }

    @Override
    public MapLiteral visitMap_literal(WdlParser.Map_literalContext ctx) {
        int id = getNextId();
        List<MapLiteral.MapEntry> entries = new ArrayList<>();
        if (ctx.expr() != null) {
            int size = ctx.expr().size();
            for (int i = 0; i < size; i += 2) {
                Expression key = visitExpr(ctx.expr(i));
                Expression value = visitExpr(ctx.expr(i + 1));
                entries.add(new MapLiteral.MapEntry(key, value));
            }
        }

        return new MapLiteral(entries, id);
    }

    @Override
    public IfThenElse visitIfthenelse(WdlParser.IfthenelseContext ctx) {
        int id = getNextId();
        Expression condition = visitExpr(ctx.expr(0));
        Expression ifTrue = visitExpr(ctx.expr(1));
        Expression ifFalse = visitExpr(ctx.expr(2));
        return new IfThenElse(condition, ifTrue, ifFalse, id);

    }

    @Override
    public DotAccessor visitGet_name(WdlParser.Get_nameContext ctx) {
        int id = getNextId();
        return new DotAccessor((Expression) visitChildren(ctx.expr_core()), ctx.Identifier().getText(), id);
    }

    @Override
    public ObjectLiteral visitObject_literal(WdlParser.Object_literalContext ctx) {
        int id = getNextId();
        Map<String, Expression> values = new HashMap<>();
        if (!ctx.isEmpty()) {
            for (int i = 0; i < ctx.Identifier().size(); i++) {
                values.put(ctx.Identifier(i).getText(), visitExpr(ctx.expr(i)));
            }
        }
        return new ObjectLiteral(values, id);
    }

    @Override
    public ArrayLiteral visitArray_literal(WdlParser.Array_literalContext ctx) {
        int id = getNextId();
        List<Expression> values = new ArrayList<>();
        if (!ctx.isEmpty()) {
            for (int i = 0; i < ctx.expr().size(); i++) {
                values.add(visitExpr(ctx.expr(i)));
            }
        }
        return new ArrayLiteral(values, id);
    }

    @Override
    public Version visitVersion(WdlParser.VersionContext ctx) {
        int id = getNextId();
        return new Version(ctx.RELEASE_VERSION().getText(), id);
    }

    @Override
    public Import.ImportAlias visitImport_alias(WdlParser.Import_aliasContext ctx) {
        int id = getNextId();
        String name = ctx.Identifier(0).getText();
        String alias = ctx.Identifier(1).getText();
        return new Import.ImportAlias(name, alias, id);
    }

    @Override
    public Import visitImport_doc(WdlParser.Import_docContext ctx) {
        int id = getNextId();
        String importUrl = ctx.string().getText();
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
        return Import.newBuilder().url(importUrl).id(id).name(name).aliases(aliases).build();
    }

    @Override
    public Struct visitStruct(WdlParser.StructContext ctx) {
        int id = getNextId();
        String name = ctx.Identifier().getText();
        List<Declaration> members = new ArrayList<>();
        if (ctx.unbound_decls() != null) {
            for (int i = 0; i < ctx.unbound_decls().size(); i++) {
                members.add(visitUnbound_decls(ctx.unbound_decls(i)));
            }
        }
        return Struct.newBuilder().name(name).members(members).id(id).build();

    }

    @Override
    public ParameterMeta visitParameter_meta(WdlParser.Parameter_metaContext ctx) {
        int id = getNextId();
        return ParameterMeta.newBuilder().attributes(visitKeyValueConetxt(ctx.meta_kv())).id(id).build();
    }

    @Override
    public Meta visitMeta(WdlParser.MetaContext ctx) {
        int id = getNextId();
        return Meta.newBuilder().attributes(visitKeyValueConetxt(ctx.meta_kv())).id(id).build();
    }

    @Override
    public Runtime visitTask_runtime(WdlParser.Task_runtimeContext ctx) {
        int id = getNextId();
        Map<String, Expression> attributes = new HashMap<>();
        if (ctx.task_runtime_kv() != null) {
            for (int i = 0; i < ctx.task_runtime_kv().size(); i++) {
                WdlParser.Task_runtime_kvContext kvContext = ctx.task_runtime_kv(i);
                String key = kvContext.Identifier().getText();
                Expression value = visitExpr(kvContext.expr());
                attributes.put(key, value);
            }
        }
        return Runtime.newBuilder().attributes(attributes).id(id).build();
    }

    @Override
    public Inputs visitTask_input(WdlParser.Task_inputContext ctx) {
        int id = getNextId();
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.any_decls() != null) {
            for (int i = 0; i < ctx.any_decls().size(); i++) {
                declarations.add(visitAny_decls(ctx.any_decls(i)));
            }
        }
        return Inputs.newBuilder().declarations(declarations).id(id).build();
    }

    @Override
    public Outputs visitTask_output(WdlParser.Task_outputContext ctx) {
        int id = getNextId();
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.bound_decls() != null) {
            for (int i = 0; i < ctx.bound_decls().size(); i++) {
                declarations.add(visitBound_decls(ctx.bound_decls(i)));
            }
        }
        return Outputs.newBuilder().declarations(declarations).id(id).build();
    }

    @Override
    public Command.CommandPart visitTask_command_expr_part(WdlParser.Task_command_expr_partContext ctx) {
        int id = getNextId();
        Expression expression = visitExpr(ctx.expr());
        List<Expression> placeholders = new ArrayList<>();
        if (ctx.expression_placeholder_option() != null) {
            for (WdlParser.Expression_placeholder_optionContext placeholder : ctx.expression_placeholder_option()) {
                placeholders.add(visitExpression_placeholder_option(placeholder));
            }
        }
        return new Command.CommandPart(placeholders, expression, id);
    }

    @Override
    public Command visitTask_command(WdlParser.Task_commandContext ctx) {
        int id = getNextId();
        List<Command.CommandPart> commandParts = new ArrayList<>();
        if (ctx.task_command_string_part() != null) {
            commandParts.add(new Command.CommandPart(ctx.task_command_string_part().getText(), id));
        }
        if (ctx.task_command_expr_with_string() != null) {
            for (WdlParser.Task_command_expr_with_stringContext part : ctx.task_command_expr_with_string()) {
                commandParts.add(visitTask_command_expr_part(part.task_command_expr_part()));
                if (part.task_command_string_part() != null) {
                    commandParts.add(new Command.CommandPart(part.task_command_string_part().getText(), getNextId()));
                }
            }
        }
        return Command.newBuilder().commandParts(commandParts).id(id).build();
    }

    @Override
    public WdlElement visitTask_element(WdlParser.Task_elementContext ctx) {
        return super.visitTask_element(ctx);
    }

    @Override
    public Task visitTask(WdlParser.TaskContext ctx) {
        int id = getNextId();
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
                   .name(name)
                   .inputs(inputs)
                   .declarations(declarations)
                   .command(command)
                   .runtime(runtime)
                   .outputs(outputs)
                   .meta(meta)
                   .parameterMeta(parameterMeta)
                   .id(id)
                   .build();
    }

    @Override
    public Call visitCall(WdlParser.CallContext ctx) {
        int id = getNextId();
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
        return Call.newBuilder().taskName(name).callAlias(alias).inputs(inputs).id(id).build();
    }

    @Override
    public Scatter visitScatter(WdlParser.ScatterContext ctx) {
        int id = getNextId();
        Scatter scatter = new Scatter();
        scatter.setId(id);
        scatter.setVarname(ctx.Identifier().getText());
        scatter.setExpression(visitExpr(ctx.expr()));
        List<WdlElement> workflowElements = new ArrayList<>();
        if (ctx.inner_workflow_element() != null) {
            for (int i = 0; i < ctx.inner_workflow_element().size(); i++) {
                WdlElement element = visitChildren(ctx.inner_workflow_element(i));
                //                if (element instanceof NamespaceElement) {
                //                    ((NamespaceElement) element).setParentNamespace(scatter);
                //                }
                workflowElements.add(element);
            }
        }

        scatter.setWorkflowElements(workflowElements);
        return scatter;
    }

    @Override
    public Conditional visitConditional(WdlParser.ConditionalContext ctx) {
        int id = getNextId();
        Conditional conditional = new Conditional();
        conditional.setExpression(visitExpr(ctx.expr()));
        conditional.setId(id);
        List<WdlElement> workflowElements = new ArrayList<>();
        if (ctx.inner_workflow_element() != null) {
            for (int i = 0; i < ctx.inner_workflow_element().size(); i++) {
                WdlElement element = visitChildren(ctx.inner_workflow_element(i));
                //                if (element instanceof NamespaceElement) {
                //                    ((NamespaceElement) element).setParentNamespace(conditional);
                //                }
                workflowElements.add(element);
            }
        }

        conditional.setElements(workflowElements);
        return conditional;
    }

    @Override
    public Inputs visitWorkflow_input(WdlParser.Workflow_inputContext ctx) {
        int id = getNextId();
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.any_decls() != null) {
            for (int i = 0; i < ctx.any_decls().size(); i++) {
                declarations.add(visitAny_decls(ctx.any_decls(i)));
            }
        }
        return Inputs.newBuilder().declarations(declarations).id(id).build();
    }

    @Override
    public Outputs visitWorkflow_output(WdlParser.Workflow_outputContext ctx) {
        int id = getNextId();
        List<Declaration> declarations = new ArrayList<>();
        if (ctx.bound_decls() != null) {
            for (int i = 0; i < ctx.bound_decls().size(); i++) {
                declarations.add(visitBound_decls(ctx.bound_decls(i)));
            }
        }
        return Outputs.newBuilder().declarations(declarations).id(id).build();
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
        int id = getNextId();
        Workflow workflow = new Workflow();
        workflow.setName(ctx.Identifier().getText());
        Inputs inputs = null;
        Outputs outputs = null;
        ParameterMeta parameterMeta = null;
        Meta meta = null;
        List<WdlElement> elements = new ArrayList<>();

        for (WdlParser.Workflow_elementContext elementContext : ctx.workflow_element()) {
            WdlElement element = visitChildren(elementContext);
            if (element instanceof Inputs) {
                inputs = (Inputs) element;
            } else if (element instanceof Outputs) {
                outputs = (Outputs) element;
            } else if (element instanceof Meta) {
                meta = (Meta) element;
            } else if (element instanceof ParameterMeta) {
                parameterMeta = (ParameterMeta) element;
            } else if (element instanceof WdlElement) {
                elements.add(element);
            }
        }

        workflow.setInputs(inputs);
        workflow.setOutputs(outputs);
        workflow.setElements(elements);
        workflow.setMeta(meta);
        workflow.setParameterMeta(parameterMeta);
        workflow.setId(id);
        return workflow;
    }

    @Override
    public WdlElement visitDocument_element(WdlParser.Document_elementContext ctx) {
        return super.visitDocument_element(ctx);
    }

    @Override
    public Document visitDocument(WdlParser.DocumentContext ctx) {
        int id = getNextId();
        Document document = new Document();
        document.setVersion(visitVersion(ctx.version()));

        List<Import> imports = new ArrayList<>();
        List<Struct> structs = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        Workflow workflow = ctx.workflow() != null ? visitWorkflow(ctx.workflow()) : null;

        document.setWorkflow(workflow);

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

        document.setImports(imports);
        document.setStructs(structs);
        document.setTasks(tasks);
        document.setId(id);
        document.setLib(new WdlV1StandardLib());
        return document;
    }
}
