package com.gtoteck.app.dao;

import android.database.Cursor;

public class GiaDungEntity {

	private int id;
	private String name;

	private String iconleft;
	private String textleft;
	private int priceleft;

	private String iconright;
	private String textright;
	private int priceright;

	public GiaDungEntity(int id, String name, String iconleft, String textleft,
			int priceleft, String iconright, String textright, int priceright) {
		this.id = id;
		this.name = name;
		this.iconleft = iconleft;
		this.textleft = textleft;
		this.priceleft = priceleft;
		this.iconright = iconright;
		this.textright = textright;
		this.priceright = priceright;
	}

	public GiaDungEntity(Cursor cursor) {
		id = cursor.getInt(0);
		name = cursor.getString(1);

		iconleft = cursor.getString(2);
		textleft = cursor.getString(3);
		priceleft = cursor.getInt(4);

		iconright = cursor.getString(5);
		textright = cursor.getString(6);
		priceright = cursor.getInt(7); 
	}

	public GiaDungEntity() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconleft() {
		return iconleft;
	}

	public void setIconleft(String iconleft) {
		this.iconleft = iconleft;
	}

	public String getTextleft() {
		return textleft;
	}

	public void setTextleft(String textleft) {
		this.textleft = textleft;
	}

	public int getPriceleft() {
		return priceleft;
	}

	public void setPriceleft(int priceleft) {
		this.priceleft = priceleft;
	}

	public String getIconright() {
		return iconright;
	}

	public void setIconright(String iconright) {
		this.iconright = iconright;
	}

	public String getTextright() {
		return textright;
	}

	public void setTextright(String textright) {
		this.textright = textright;
	}

	public int getPriceright() {
		return priceright;
	}

	public void setPriceright(int priceright) {
		this.priceright = priceright;
	}

	@Override
	public String toString() {
		return "GiaDungEntity [id=" + id + ", name=" + name + ", iconleft="
				+ iconleft + ", textleft=" + textleft + ", priceleft="
				+ priceleft + ", iconright=" + iconright + ", textright="
				+ textright + ", priceright=" + priceright + "]";
	}
}
