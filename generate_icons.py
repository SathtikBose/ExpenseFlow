import os
from PIL import Image, ImageDraw

source_image_path = r"C:\Users\satht\.gemini\antigravity\brain\b2f3f18e-d300-42b6-9399-ba876178bb4d\expenseflow_icon_1780067013052.png"
base_res_dir = r"C:\Users\satht\OneDrive\Documents\Youtube\Projects\ExpenseFlow\app\src\main\res"

sizes = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192
}

img = Image.open(source_image_path).convert("RGBA")

# Create a circular mask for round icons
def make_round(im, size):
    mask = Image.new('L', (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.ellipse((0, 0, size, size), fill=255)
    
    result = im.copy()
    result.putalpha(mask)
    return result

for density, size in sizes.items():
    # Resize image
    resized = img.resize((size, size), Image.Resampling.LANCZOS)
    
    # Save standard icon
    out_dir = os.path.join(base_res_dir, f"mipmap-{density}")
    if not os.path.exists(out_dir):
        os.makedirs(out_dir)
        
    resized.save(os.path.join(out_dir, "ic_launcher.png"))
    
    # Save round icon
    round_img = make_round(resized, size)
    round_img.save(os.path.join(out_dir, "ic_launcher_round.png"))

print("Icons generated successfully.")
