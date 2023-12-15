package edu.hw11.Task3;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;


@SuppressWarnings({"MagicNumber", "AnonInnerLength"})
public class FibonachiGenerator {
    public Class<?> generateFibonachiClass() throws Exception {
        return new ByteBuddy()
            .subclass(Object.class)
            .name("FibonacciCounter")
            .defineMethod("fib", long.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameter(int.class, "n")
            .intercept(new Implementation.Simple(new ByteCodeAppender() {
                @Override
                public Size apply(
                    MethodVisitor methodVisitor,
                    Implementation.Context context,
                    MethodDescription methodDescription
                ) {
                    Label startLabel = new Label();
                    Label loopLabel = new Label();
                    Label endLabel = new Label();
                    methodVisitor.visitCode();

                    methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
                    methodVisitor.visitJumpInsn(Opcodes.IFNE, startLabel);
                    methodVisitor.visitInsn(Opcodes.LCONST_0);
                    methodVisitor.visitInsn(Opcodes.LRETURN);

                    methodVisitor.visitFrame(
                        Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 0, new Object[] {}
                    );

                    methodVisitor.visitLabel(startLabel);
                    methodVisitor.visitInsn(Opcodes.LCONST_0);
                    methodVisitor.visitVarInsn(Opcodes.LSTORE, 1);
                    methodVisitor.visitInsn(Opcodes.LCONST_1);
                    methodVisitor.visitVarInsn(Opcodes.LSTORE, 3);
                    methodVisitor.visitInsn(Opcodes.ICONST_0);
                    methodVisitor.visitVarInsn(Opcodes.ISTORE, 5);

                    methodVisitor.visitFrame(
                        Opcodes.F_FULL, 4, new Object[] {Opcodes.INTEGER, Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER},
                        0, new Object[] {}
                    );
                    methodVisitor.visitLabel(loopLabel);
                    methodVisitor.visitVarInsn(Opcodes.ILOAD, 5);
                    methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
                    methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, endLabel);

                    methodVisitor.visitVarInsn(Opcodes.LLOAD, 3);
                    methodVisitor.visitVarInsn(Opcodes.LSTORE, 6);
                    methodVisitor.visitVarInsn(Opcodes.LLOAD, 1);
                    methodVisitor.visitVarInsn(Opcodes.LLOAD, 3);
                    methodVisitor.visitInsn(Opcodes.LADD);
                    methodVisitor.visitVarInsn(Opcodes.LSTORE, 3);
                    methodVisitor.visitVarInsn(Opcodes.LLOAD, 6);
                    methodVisitor.visitVarInsn(Opcodes.LSTORE, 1);

                    methodVisitor.visitIincInsn(5, 1);
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, loopLabel);

                    methodVisitor.visitFrame(
                        Opcodes.F_FULL, 4, new Object[] {Opcodes.INTEGER, Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER},
                        0, new Object[] {}
                    );
                    methodVisitor.visitLabel(endLabel);
                    methodVisitor.visitVarInsn(Opcodes.LLOAD, 1);
                    methodVisitor.visitInsn(Opcodes.LRETURN);

                    methodVisitor.visitEnd();

                    return new Size(4, 8);
                }
            }))
            .make()
            .load(ClassLoader.getSystemClassLoader())
            .getLoaded();
    }
}
