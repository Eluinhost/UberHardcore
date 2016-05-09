/*
 * Project: UberHardcore
 * Class: gg.uhc.uberhardcore.nms.v1_8_R3.AIUtil
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Graham Howden <graham_howden1 at yahoo.co.uk>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package gg.uhc.uberhardcore.nms.v1_9_R1;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.server.v1_9_R1.PathfinderGoal;
import net.minecraft.server.v1_9_R1.PathfinderGoalSelector;

import java.lang.reflect.Field;
import java.util.List;

public class AIUtil {

    protected final Field taskEntriesField;
    protected final Field actionField;
    protected final Field priorityField;

    protected AIUtil() throws NoSuchFieldException, ClassNotFoundException {
        Class pathfinderGoalSelectorItem = Class.forName(PathfinderGoalSelector.class.getName() + "$PathfinderGoalSelectorItem");
        actionField = pathfinderGoalSelectorItem.getDeclaredField("a");
        actionField.setAccessible(true);
        priorityField = pathfinderGoalSelectorItem.getDeclaredField("b");
        priorityField.setAccessible(true);

        taskEntriesField = PathfinderGoalSelector.class.getDeclaredField("b");
        taskEntriesField.setAccessible(true);
    }

    protected static AIUtil INSTANCE;

    public static AIUtil getInstance() {
        if (null == INSTANCE) {
            try {
                INSTANCE = new AIUtil();
            } catch (ReflectiveOperationException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        return INSTANCE;
    }

    public void removeAIGoalsByPriority(Multimap<Integer, Class<? extends PathfinderGoal>> toRemove, PathfinderGoalSelector selector) {
        List<?> entries;
        try {
            entries = (List<?>) taskEntriesField.get(selector);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        List<PathfinderGoal> found = Lists.newArrayList();

        // check each existing AI
        for (Object entry : entries) {
            try {
                // grab the goal + priority from the entry
                PathfinderGoal goal = (PathfinderGoal) actionField.get(entry);
                int priority = priorityField.getInt(entry);

                // if we want to remove it add it to the list
                if (toRemove.containsEntry(null, goal.getClass()) || toRemove.containsEntry(priority, goal.getClass())) {
                    found.add(goal);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // remove each goal
        for (PathfinderGoal goal : found) {
            selector.a(goal);
        }
    }

    public void removeAIGoals(PathfinderGoalSelector selector, Class<? extends PathfinderGoal>... toRemove) {
        Multimap<Integer, Class<? extends PathfinderGoal>> map = HashMultimap.create(1, toRemove.length);

        map.putAll(null, Lists.newArrayList(toRemove));

        removeAIGoalsByPriority(map, selector);
    }

    public void removeAllGoals(PathfinderGoalSelector selector) {
        try {
            // copy into new list
            List entries = Lists.newArrayList((List) taskEntriesField.get(selector));

            // remove each entry from the selector
            for (Object entry : entries) {
                selector.a((PathfinderGoal) actionField.get(entry));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
