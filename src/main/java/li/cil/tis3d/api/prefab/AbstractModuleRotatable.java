package li.cil.tis3d.api.prefab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.tis3d.api.API;
import li.cil.tis3d.api.Casing;
import li.cil.tis3d.api.Face;
import li.cil.tis3d.api.Port;
import li.cil.tis3d.api.module.Rotatable;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

/**
 * This is a utility implementation of a rotatable module.
 * <p>
 * Rotatable modules can face one of four directions, the default being
 * {@link li.cil.tis3d.api.Port#UP}. Most modules will either not need
 * this at all, or only use this when installed in the top or bottom faces
 * of casings. In some cases you may also merely want to use this for
 * graphical purposes (e.g. the built-in redstone and stack modules do
 * this).
 */
public abstract class AbstractModuleRotatable extends AbstractModule implements Rotatable {
    // --------------------------------------------------------------------- //
    // Persisted data

    private Port facing = Port.UP;

    // --------------------------------------------------------------------- //

    protected AbstractModuleRotatable(final Casing casing, final Face face) {
        super(casing, face);
    }

    // --------------------------------------------------------------------- //

    /**
     * Apply the module's rotation to the OpenGL state.
     */
    @SideOnly(Side.CLIENT)
    protected void rotateForRendering() {
        final int rotation = Port.ROTATION[facing.ordinal()];
        GL11.glTranslatef(0.5f, 0.5f, 0);
        GL11.glRotatef(90 * rotation, 0, 0, Face.toEnumFacing(getFace()).getFrontOffsetY());
        GL11.glTranslatef(-0.5f, -0.5f, 0);
    }

    // --------------------------------------------------------------------- //
    // Module

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey("facing")) {
            try {
                facing = Enum.valueOf(Port.class, nbt.getString("facing"));
            } catch (final IllegalArgumentException e) {
                // This can only happen if someone messes with the save.
                LogManager.getLogger(API.MOD_ID).warn("Broken save, execution module facing is invalid.", e);
            }
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setString("facing", facing.name());
    }

    // --------------------------------------------------------------------- //
    // Rotatable

    @Override
    public Port getFacing() {
        return facing;
    }

    @Override
    public void setFacing(final Port facing) {
        this.facing = facing;
    }
}