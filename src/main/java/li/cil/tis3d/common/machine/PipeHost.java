package li.cil.tis3d.common.machine;

import li.cil.tis3d.api.machine.Face;
import li.cil.tis3d.api.machine.Port;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Abstraction layer for pipe containers, provides positional awareness.
 */
public interface PipeHost {
    World getPipeHostWorld();

    BlockPos getPipeHostPosition();

    default void onBeforeWriteComplete(final Face sendingFace, final Port sendingPort) {
    }

    default void onWriteComplete(final Face sendingFace, final Port sendingPort) {
    }
}
