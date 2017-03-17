/*
 * Copyright (C) 2017 Kasirgalabs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kasirgalabs.etumulator.processor.instruction.shift;

import static org.junit.Assert.assertEquals;

import com.kasirgalabs.etumulator.InstructionTester;
import org.junit.Test;

public class RrxInstructionTest extends InstructionTester {
    /**
     * Test of exitRrx and exitRrxs method, of class Processor.
     */
    @Test
    public void exitRrxAndExitRrxs() {
        String code = "mov r1, 1\n"
                + "rrx r0, r1\n";
        runTestCode(code);
        assertEquals("Shift result is wrong.", 0, registerFile.getValue("r0"));

        cpsr.setCarry(true);
        code = "ldr r1, =#0xffffffff\n"
                + "rrxs r0, r1\n";
        runTestCode(code);
        assertEquals("Shift result is wrong.", 0xffffffff, registerFile.getValue("r0"));
        assertEquals("Negative flag is wrong.", true, cpsr.isNegative());
        assertEquals("Zero flag is wrong.", false, cpsr.isZero());
        assertEquals("Carry flag is wrong.", true, cpsr.isCarry());

        cpsr.setCarry(false);
        code = "ldr r1, =#0xffffffff\n"
                + "rrxs r0, r1\n";
        runTestCode(code);
        assertEquals("Shift result is wrong.", Integer.MAX_VALUE, registerFile.getValue("r0"));
        assertEquals("Negative flag is wrong.", false, cpsr.isNegative());
        assertEquals("Zero flag is wrong.", false, cpsr.isZero());
        assertEquals("Carry flag is wrong.", true, cpsr.isCarry());

        cpsr.setCarry(false);
        code = "mov r1, 0\n"
                + "rrxs r0, r1\n";
        runTestCode(code);
        assertEquals("Shift result is wrong.", 0, registerFile.getValue("r0"));
        assertEquals("Negative flag is wrong.", false, cpsr.isNegative());
        assertEquals("Zero flag is wrong.", true, cpsr.isZero());
        assertEquals("Carry flag is wrong.", false, cpsr.isCarry());

        cpsr.setCarry(false);
        code = "mov r1, 1\n"
                + "rrxs r0, r1\n";
        runTestCode(code);
        assertEquals("Shift result is wrong.", 0, registerFile.getValue("r0"));
        assertEquals("Negative flag is wrong.", false, cpsr.isNegative());
        assertEquals("Zero flag is wrong.", true, cpsr.isZero());
        assertEquals("Carry flag is wrong.", true, cpsr.isCarry());
    }
}