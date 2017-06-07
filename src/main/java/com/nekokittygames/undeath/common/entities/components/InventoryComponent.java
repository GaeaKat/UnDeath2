package com.nekokittygames.undeath.common.entities.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

/**
 * Created by katsw on 06/06/2017.
 */
public class InventoryComponent {

    private NonNullList<ItemStack> inventory;

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    public NonNullList<ItemStack> getArmor() {
        return armor;
    }

    public NonNullList<ItemStack> getOffhand() {
        return offhand;
    }

    private NonNullList<ItemStack> armor;
    private NonNullList<ItemStack> offhand;
    public InventoryComponent()
    {
        inventory=NonNullList.withSize(36,ItemStack.EMPTY);
        armor=NonNullList.withSize(4,ItemStack.EMPTY);
        offhand=NonNullList.withSize(1,ItemStack.EMPTY);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound cmp)
    {
        NBTTagList list=new NBTTagList();
        for(int i=0;i<inventory.size();i++)
        {
            NBTTagCompound invDetail=new NBTTagCompound();
            invDetail.setInteger("invSlot",i);
            NBTTagCompound invItem=new NBTTagCompound();
            inventory.get(i).writeToNBT(invItem);
            invDetail.setTag("invItem",invItem);
            list.appendTag(invDetail);
        }
        cmp.setTag("inventory",list);
        list=new NBTTagList();
        for(int i=0;i<armor.size();i++)
        {
            NBTTagCompound invDetail=new NBTTagCompound();
            invDetail.setInteger("invSlot",i);
            NBTTagCompound invItem=new NBTTagCompound();
            armor.get(i).writeToNBT(invItem);
            invDetail.setTag("invItem",invItem);
            list.appendTag(invDetail);
        }
        cmp.setTag("armor",list);
        list=new NBTTagList();
        for(int i=0;i<offhand.size();i++)
        {
            NBTTagCompound invDetail=new NBTTagCompound();
            invDetail.setInteger("invSlot",i);
            NBTTagCompound invItem=new NBTTagCompound();
            offhand.get(i).writeToNBT(invItem);
            invDetail.setTag("invItem",invItem);
            list.appendTag(invDetail);
        }
        cmp.setTag("offhand",list);
        return cmp;
    }

    public NBTTagCompound readFromNBT(NBTTagCompound cmp)
    {
        if(cmp!=null) {
            inventory.clear();
            armor.clear();
            offhand.clear();
            NBTTagList list = cmp.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
            NBTTagCompound invDetail;
            for (int i = 0; i < list.tagCount(); i++) {
                invDetail = list.getCompoundTagAt(i);
                int invSlot = invDetail.getInteger("invSlot");
                ItemStack invItem = new ItemStack(invDetail.getCompoundTag("invItem"));
                inventory.set(invSlot, invItem);
            }


            NBTTagList armorList = cmp.getTagList("armor", Constants.NBT.TAG_COMPOUND);
            NBTTagCompound armorDetail;
            for (int i = 0; i < armorList.tagCount(); i++) {
                armorDetail = armorList.getCompoundTagAt(i);
                int invSlot = armorDetail.getInteger("invSlot");
                ItemStack invItem = new ItemStack(armorDetail.getCompoundTag("invItem"));
                armor.set(invSlot, invItem);
            }


            NBTTagList offhandList = cmp.getTagList("offhand", Constants.NBT.TAG_COMPOUND);
            NBTTagCompound offhandDetail;
            for (int i = 0; i < offhandList.tagCount(); i++) {
                offhandDetail = offhandList.getCompoundTagAt(i);
                int invSlot = offhandDetail.getInteger("invSlot");
                ItemStack invItem = new ItemStack(offhandDetail.getCompoundTag("invItem"));
                offhand.set(invSlot, invItem);
            }
        }
        return cmp;
    }

    public void CopyInventory(EntityPlayer player)
    {
        InventoryPlayer playerInv=player.inventory;

        for(int i=0;i<playerInv.mainInventory.size();i++)
        {
            inventory.set(i,playerInv.mainInventory.get(i).copy());
        }
        for(int i=0;i<playerInv.armorInventory.size();i++)
        {
            armor.set(i,playerInv.armorInventory.get(i).copy());
        }
        for(int i=0;i<playerInv.offHandInventory.size();i++)
        {
            offhand.set(i,playerInv.offHandInventory.get(i).copy());
        }
    }

}
